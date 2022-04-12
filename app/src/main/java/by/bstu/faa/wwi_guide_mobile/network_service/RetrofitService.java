package by.bstu.faa.wwi_guide_mobile.network_service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
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
                .baseUrl(CONSTANTS.URLS.BASE_LOCALHOST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static RetrofitService getInstance() {
        if (mInstance == null) { mInstance = new RetrofitService(); }
        return mInstance;
    }

    public AppApi getAppApi() {
        return mRetrofit.create(AppApi.class);
    }

    public static boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
