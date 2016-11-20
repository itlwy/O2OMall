package itlwy.com.o2omall.data;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import itlwy.com.o2omall.ConstantValue;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by mac on 16/11/19.
 */
public class CommonRepository {

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;

    private static CommonRepository ourInstance = new CommonRepository();

    public static CommonRepository getInstance() {
        return ourInstance;
    }

    private CommonRepository() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor()//log，统一的header等
        {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
//                long t1 = System.nanoTime();
//                logger.info(String.format("Sending request %s on %s%n%s",
//                        request.url(), chain.connection(), request.headers()));
                Response response = chain.proceed(request);
//                long t2 = System.nanoTime();
//                logger.info(String.format("Received response for %s in %.1fms%n%s",
//                        response.request().url(), (t2 - t1) / 1e6d, response.headers()));
                return response;
            }
        }).build();
        retrofit = new Retrofit.Builder()
                .client(client)
//                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ConstantValue.BASE_URL)
                .build();

    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public static <T> void processResult(Observable<HttpResultModel<T>> observable,
                                         HttpResultFunc<T> resultFunc, Subscriber<T> subscriber) {
        observable.map(resultFunc)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
