package com.example.minhquan.w2vinova.News.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.minhquan.w2vinova.EndlessRecyclerViewScrollListener;
import com.example.minhquan.w2vinova.Adapter.NewsAdapter;
import com.example.minhquan.w2vinova.Model.Doc;
import com.example.minhquan.w2vinova.News.presenter.ListNewsPresenter;
import com.example.minhquan.w2vinova.News.presenter.ListNewsPresenterImpl;
import com.example.minhquan.w2vinova.News.repository.NewsRepository;
import com.example.minhquan.w2vinova.News.repository.NewsRepositoryImpl;
import com.example.minhquan.w2vinova.R;
import com.example.minhquan.w2vinova.Util.RetrofitUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ListNewsView{
    NewsAdapter newsAdapter;
    ListNewsPresenter presenter;
    EndlessRecyclerViewScrollListener scrollListener;
    String date = "", date_picker = "";
    String SortOrder;
    String news_desk = "";
    boolean save = false;
    boolean arts = false, fashion_style = false, sport = false;
    int pageNews = 1;
    int pageSearch = 1;
    int pageFilter = 1;

    @BindView(R.id.toolBar) Toolbar toolbar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        NewsRepository repository = new NewsRepositoryImpl();
        presenter = new ListNewsPresenterImpl(repository,this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            date = bundle.getString("Date");
            date_picker = bundle.getString("DatePicker");
            SortOrder = bundle.getString("SortOrder").toLowerCase();
            arts = bundle.getBoolean("Arts");
            fashion_style = bundle.getBoolean("FashionStyle");
            sport = bundle.getBoolean("Sports");
            news_desk = "news_desk:( ";
            if(arts){
                news_desk = news_desk + "Arts ";
            }
            if(fashion_style){
                news_desk = news_desk + "Fashion Style ";
            }
            if(sport){
                news_desk = news_desk + "Sports ";
            }
            news_desk = news_desk + ")";
            setUpListNews(news_desk);
            presenter.getNewsFilter(date,SortOrder,news_desk,pageFilter);
        }
        else {
            setUpListNews("");
            presenter.getNews(pageNews);
        }



    }


    private void setUpListNews(final String fq) {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        newsAdapter = new NewsAdapter(MainActivity.this);
        recyclerView.setAdapter(newsAdapter);

        scrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(fq.equals("")){
                    pageNews++;
                    presenter.getNews(pageNews);
                }
                else if (!fq.equals("") && date.equals("")){
                    pageSearch++;
                    presenter.getNewsSearch(fq,pageSearch);
                }
                else{
                    pageFilter++;
                    presenter.getNewsFilter(date,SortOrder,news_desk,pageFilter);
                }

            }
        };
        recyclerView.addOnScrollListener(scrollListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar,menu);

        // Get the SearchView and set the searchable configuration
        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.seach_menu).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                setUpListNews("s");
                //scrollListener.resetState();
                presenter.getNewsSearch(s,pageSearch);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.filter:{
                Intent intent = new Intent(MainActivity.this,FilterActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("Date", date);
                bundle2.putString("DatePicker", date_picker);
                bundle2.putString("SortOrder",SortOrder);
                bundle2.putBoolean("Arts",arts);
                bundle2.putBoolean("FashionStyle",fashion_style);
                bundle2.putBoolean("Sports",sport);
                intent.putExtras(bundle2);
                startActivity(intent);

                return true;
            }

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if(progressBar.isShown()){
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showListNews(List<Doc> docs) {
        newsAdapter.setData(docs);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

}
