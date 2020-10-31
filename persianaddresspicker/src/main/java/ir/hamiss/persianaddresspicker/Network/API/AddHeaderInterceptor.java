package ir.hamiss.persianaddresspicker.Network.API;

import java.io.IOException;



import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Accept", "Application/json");


        return chain.proceed(builder.build());

    }
}