package by.bstu.faa.wwi_guide_mobile.network_service;

import by.bstu.faa.wwi_guide_mobile.constants.Constants;
import by.bstu.faa.wwi_guide_mobile.network_service.api.UserApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private Retrofit mRetrofit;

    private NetworkService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Watch Request and Response
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.Values.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public UserApi getUserApi() {
        return mRetrofit.create(UserApi.class);
    }

    public <T> T buildService(Class<T> service) {
        return mRetrofit.create(service);
    }
}
