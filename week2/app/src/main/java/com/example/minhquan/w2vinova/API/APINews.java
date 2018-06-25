package com.example.minhquan.w2vinova.API;

import com.example.minhquan.w2vinova.Model.DataSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APINews {

    @GET("/svc/search/v2/articlesearch.json")
    Call<DataSearch> getDataDefault(@Query("page") int page
    );

    @GET("/svc/search/v2/articlesearch.json")
    Call<DataSearch> getDataSearch(@Query("fq") String fq,
                                   @Query("page") int page
    );

    @GET("/svc/search/v2/articlesearch.json")
    Call<DataSearch> getDataFilter(@Query("begin_date") String begin_date,
                                   @Query("sort") String sort,
                                   @Query("fq") String fq,
                                   @Query("page") int page
    );
}
