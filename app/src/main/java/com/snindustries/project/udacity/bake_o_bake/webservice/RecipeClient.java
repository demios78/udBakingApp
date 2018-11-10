package com.snindustries.project.udacity.bake_o_bake.webservice;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Shaaz Noormohammad
 * (c) 11/9/18
 */
public class RecipeClient {

    private static RecipeClient INSTANCE;
    private final RecipeApi api;
    private final Retrofit retrofit;

    private RecipeClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                //.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Params.baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        api = createApi();
    }

    public static RecipeClient get() {
        if (INSTANCE == null) {
            synchronized (RecipeClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RecipeClient();
                }
            }
        }
        return INSTANCE;
    }

    public RecipeApi getApi() {
        return api;
    }

    private RecipeApi createApi() {
        return retrofit.create(RecipeApi.class);
    }

    private static class Params {
        static final String baseUrl = "http://go.udacity.com/";
    }
}
