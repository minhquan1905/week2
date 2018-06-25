package com.example.minhquan.w2vinova.Util;

import com.example.minhquan.w2vinova.API.APINews;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    private static final String BASE_URL = "http://api.nytimes.com";
    private static final String API_KEY = "68a6fb382f084740889c82ef713fc947";

    private static Retrofit builder(){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(client())
                .build();
    }

    private static OkHttpClient client(){
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url()
                                .newBuilder()
                                .addQueryParameter("api-key", API_KEY)
                                .build();
                        request = request.newBuilder()
                                .url(url)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
    }

    public static APINews createService(){
        return builder().create(APINews.class);
    }
}
