package ir.hamiss.persianaddresspicker.Network.API;








import java.util.concurrent.TimeUnit;




import ir.hamiss.persianaddresspicker.R;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



public class APIService {
    private static APIClient apiClient = null;


    private APIService() {
    }

    public static APIClient getApiClient() {
        if (apiClient  == null ) {










            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

// add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!


            httpClient .connectTimeout(40, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS);




            Retrofit.Builder builder = new Retrofit.Builder();
            builder


                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())

                    .baseUrl("https://hamiss.ir");



            Retrofit retrofit = builder.build();
            apiClient = retrofit.create(APIClient.class);
        }
        return apiClient;
    }



}
