package com.example.minhquan.w2vinova.News.view;

import com.example.minhquan.w2vinova.Model.Doc;

import java.util.List;

public interface ListNewsView {
    void showLoading();

    void hideLoading();

    void showListNews(List<Doc> docs);

    void showError(String error);

}
