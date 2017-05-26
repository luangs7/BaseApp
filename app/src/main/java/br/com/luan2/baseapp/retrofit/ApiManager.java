package br.com.luan2.baseapp.retrofit;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by DevMaker on 9/28/16.
 */
public class ApiManager {
    OkHttpClient okHttpClient;
    Retrofit retrofit;
   public static String  endpoint = "";
    Context context;

    public ApiManager(Context context){
        this.context = context;
        endpoint = "https://imobicast.herokuapp.com/api/v1/";

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30000*6, TimeUnit.MILLISECONDS)
                .readTimeout(30000*6, TimeUnit.MILLISECONDS)
                .writeTimeout(30000*6, TimeUnit.MILLISECONDS)
                .addInterceptor(logInterceptor)
                .build();



        Gson gson = new GsonBuilder()
                .create();


        retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    
    public ApiManager(Retrofit retrofit, OkHttpClient okHttpClient) {
        this.retrofit = retrofit;
        this.okHttpClient = okHttpClient;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public ApiManager(Context context,Retrofit retrofit, OkHttpClient okHttpClient) {
        this.context = context;
        this.retrofit = retrofit;
        this.okHttpClient = okHttpClient;
    }


}
