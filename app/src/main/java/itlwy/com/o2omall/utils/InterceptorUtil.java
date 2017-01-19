package itlwy.com.o2omall.utils;

import java.io.IOException;

import itlwy.com.o2omall.data.ClientKernal;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by mac on 16/12/4.
 */
public class InterceptorUtil {
    private Interceptor mInterceptor;

    private static InterceptorUtil ourInstance = new InterceptorUtil();

    public static InterceptorUtil getInstance() {
        return ourInstance;
    }

    private InterceptorUtil() {
        mInterceptor = new Interceptor()//log，统一的header等
        {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                if ("post".equalsIgnoreCase(originalRequest.method()) &&
                        ClientKernal.getInstance().getUserModel() != null) {
                    Request authorised = originalRequest.newBuilder()
                            .header("authorization", ClientKernal.getInstance().getUserModel().getToken())
                            .build();
                    return chain.proceed(authorised);
                } else
                    return chain.proceed(originalRequest);
//                Request request = chain.request();
//                long t1 = System.nanoTime();
//                logger.info(String.format("Sending request %s on %s%n%s",
//                        request.url(), chain.connection(), request.headers()));
//                Response response = chain.proceed(request);
//                long t2 = System.nanoTime();
//                logger.info(String.format("Received response for %s in %.1fms%n%s",
//                        response.request().url(), (t2 - t1) / 1e6d, response.headers()));
//                return response;
            }
        };
    }

    public Interceptor getInterceptor() {
        return mInterceptor;
    }
}
