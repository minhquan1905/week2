package com.example.minhquan.w2vinova.News.presenter;

public interface ListNewsPresenter {
    void getNews(int pageNews);
    void getNewsSearch(String fq, int pageNews);
    void getNewsFilter(String begin_date, String sort, String fq, int pageNews);

}
