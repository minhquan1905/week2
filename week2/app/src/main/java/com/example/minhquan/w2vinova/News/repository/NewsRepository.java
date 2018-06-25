package com.example.minhquan.w2vinova.News.repository;

public interface NewsRepository {
    void getDataFromNetWork(DataListener listener, int pageNews);
    void getDataSearchFromNetWork(DataListener listener,String fq, int pageNews);
    void getDataFilterFromNetWork(DataListener listener,String begin_date, String sort, String fq, int pageNews);
    void getDataFromLocal(DataListener listener);
}
