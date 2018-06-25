package com.example.minhquan.w2vinova.News.presenter;

import com.example.minhquan.w2vinova.Model.Doc;
import com.example.minhquan.w2vinova.News.repository.DataListener;
import com.example.minhquan.w2vinova.News.repository.NewsRepository;
import com.example.minhquan.w2vinova.News.view.ListNewsView;

import java.util.List;

public class ListNewsPresenterImpl implements ListNewsPresenter, DataListener{
    private NewsRepository newsRepository;
    private ListNewsView listNewsView;



    public ListNewsPresenterImpl(NewsRepository newsRepository, ListNewsView listNewsView) {
        this.newsRepository = newsRepository;
        this.listNewsView = listNewsView;

    }

    @Override
    public void getNews(int pageNews) {
        listNewsView.showLoading();
        newsRepository.getDataFromNetWork(this, pageNews);

    }

    @Override
    public void getNewsSearch(String fq, int pageNews) {
        listNewsView.showLoading();
        newsRepository.getDataSearchFromNetWork(this, fq, pageNews);
    }

    @Override
    public void getNewsFilter(String begin_date, String sort, String fq, int pageNews) {
        listNewsView.showLoading();
        newsRepository.getDataFilterFromNetWork(this, begin_date,sort,fq, pageNews);
    }


    @Override
    public void onResponse(List<Doc> docs) {
        listNewsView.hideLoading();
        listNewsView.showListNews(docs);
    }

    @Override
    public void onError(String error) {
        listNewsView.hideLoading();
        listNewsView.showError(error);

    }

}
