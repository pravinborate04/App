package com.mastek.appengage;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pravin103082 on 02-02-2017.
 */

public class RetrofitManager {

    WebServices webServices;
    private static Retrofit retrofit;
    static RetrofitManager retrofitManager;

    public static RetrofitManager getInstance() {

        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {

                retrofitManager = new RetrofitManager();

                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.connectTimeout(120, TimeUnit.SECONDS);
                httpClient.readTimeout(120, TimeUnit.SECONDS);
                retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }
        return retrofitManager;
    }


    public WebServices call() {
        webServices = retrofit.create(WebServices.class);
        return webServices;
    }
}
