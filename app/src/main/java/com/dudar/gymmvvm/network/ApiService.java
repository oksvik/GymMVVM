package com.dudar.gymmvvm.network;

import com.dudar.gymmvvm.app.GymConst;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static ApiService apiServiceInstance;
    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient;

    private ApiService() {
        initOkHttp();

        retrofit = new Retrofit.Builder()
                .baseUrl(GymConst.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ApiService getInstance(){
        if (apiServiceInstance == null) {
            apiServiceInstance = new ApiService();
        }
        return apiServiceInstance;
    }

    public GymApi getGymApi(){
        return retrofit.create(GymApi.class);
    }

    private static void initOkHttp(){
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);

        okHttpClient = httpClient.build();
    }


}
