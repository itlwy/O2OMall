package itlwy.com.o2omall.data;

import itlwy.com.o2omall.utils.InterceptorUtil;
import com.lndroid.lndroidlib.data.HttpResultFunc;
import com.lndroid.lndroidlib.data.HttpResultModel;

import java.util.concurrent.TimeUnit;

import itlwy.com.o2omall.ConstantValue;
import okhttp3.OkHttpClient;
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

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor
                (InterceptorUtil.getInstance().getInterceptor()).build();
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
