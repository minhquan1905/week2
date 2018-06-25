package com.example.minhquan.w2vinova.News.repository;

import com.example.minhquan.w2vinova.Model.Doc;

import java.util.List;

public interface DataListener {
    void onResponse(List<Doc> docs);
    void onError(String error);
}
