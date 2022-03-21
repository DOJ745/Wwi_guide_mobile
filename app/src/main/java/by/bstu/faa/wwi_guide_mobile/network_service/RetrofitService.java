package by.bstu.faa.wwi_guide_mobile.network_service;

import by.bstu.faa.wwi_guide_mobile.constants.Constants;
import by.bstu.faa.wwi_guide_mobile.network_service.api.AppApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private static RetrofitService mInstance;
    private final Retrofit mRetrofit;

    private RetrofitService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Watch Request and Response for logs in IDE
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.Values.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static RetrofitService getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitService();
        }
        return mInstance;
    }

    public AppApi getAppApi() {
        return mRetrofit.create(AppApi.class);
    }

    public <T> T buildService(Class<T> service) {
        return mRetrofit.create(service);
    }
}
