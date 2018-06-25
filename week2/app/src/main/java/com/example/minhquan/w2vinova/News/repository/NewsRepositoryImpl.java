package com.example.minhquan.w2vinova.News.repository;

import com.example.minhquan.w2vinova.API.APINews;
import com.example.minhquan.w2vinova.Model.DataSearch;
import com.example.minhquan.w2vinova.Model.Doc;
import com.example.minhquan.w2vinova.News.presenter.ListNewsPresenter;
import com.example.minhquan.w2vinova.News.presenter.ListNewsPresenterImpl;
import com.example.minhquan.w2vinova.News.view.MainActivity;
import com.example.minhquan.w2vinova.Util.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepositoryImpl implements NewsRepository {
    private APINews apiNews;
    List<Doc> docs = new ArrayList<>();



    public NewsRepositoryImpl() {
        apiNews = RetrofitUtil.createService();
    }

    @Override
    public void getDataFromNetWork(final DataListener listener, int pageNews) {
        apiNews.getDataDefault(pageNews).enqueue(new Callback<DataSearch>() {
            @Override
            public void onResponse(Call<DataSearch> call, Response<DataSearch> response) {
                if (response.body() != null) {
                    docs.addAll(response.body().getResponse().getDocs());
                    listener.onResponse(docs);

                } else {
                    //listener.onError("Can't get response default");
                }
            }
            @Override
            public void onFailure(Call<DataSearch> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });

    }

    @Override
    public void getDataSearchFromNetWork(final DataListener listener, String fq, int pageNews) {
        apiNews.getDataSearch(fq, pageNews).enqueue(new Callback<DataSearch>() {
            @Override
            public void onResponse(Call<DataSearch> call, Response<DataSearch> response) {
                if (response.body() != null) {
                    List<Doc> docs_search = new ArrayList<>();
                    docs_search.addAll(response.body().getResponse().getDocs());
                    listener.onResponse(docs_search);

                } else {
                    //listener.onError("Can't get response Search");
                }
            }

            @Override
            public void onFailure(Call<DataSearch> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getDataFilterFromNetWork(final DataListener listener, String begin_date, String sort, String fq, int pageNews) {
        apiNews.getDataFilter(begin_date, sort, fq, pageNews).enqueue(new Callback<DataSearch>() {
            @Override
            public void onResponse(Call<DataSearch> call, Response<DataSearch> response) {
                if (response.body() != null) {
                    List<Doc> docs_filter = new ArrayList<>();
                    docs_filter.addAll(response.body().getResponse().getDocs());
                    listener.onResponse(docs_filter);

                } else {
                    //listener.onError("Can't get response Filter");
                }
            }

            @Override
            public void onFailure(Call<DataSearch> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getDataFromLocal(DataListener listener) {

    }
}
