package com.example.marco.faq.article;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marco.faq.R;
import com.example.marco.faq.category.CategoryActivity;
import com.example.marco.faq.detail.DetailActivity;
import com.example.marco.faq.models.Article;
import com.example.marco.faq.models.Category;
import com.example.marco.faq.web.WebCommunication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Marco on 05.10.2017.
 */

public class ArticleActivity extends AppCompatActivity implements Callback {

    private ListView articlelistview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        articlelistview = (ListView) findViewById(R.id.articlelistview);

        Intent intent = getIntent();
        String categoryId = intent.getStringExtra("currentCategoryId");
        Logger.d(categoryId);

        WebCommunication webCommunication = new WebCommunication();

        String url = "https://public-supportcenter-cbw.lyoness.com/de-AT/rest/faq/articles/" + categoryId;

        webCommunication.performRequest(url,this);
    }


    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

        ResponseBody responseBody = response.body();

        if(responseBody != null){
            String responseString = responseBody.string();
            Logger.json(responseString);

            Gson gson = new Gson();

            Type listType = new TypeToken<List<Article>>() {}.getType();

            final List<Article> articles = gson.fromJson(responseString, listType);

            for(int i=0; i<articles.size();i++){
                Article article = articles.get(i);
                Logger.d("ArticleId: %s, Title: %s", article.getArticle_id(), article.getQuestion());
            }
            Handler mainHandler = new Handler(getMainLooper());
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    showArticleList(articles);
                }
            };
            mainHandler.post(runnable);
        }

    }

    public void showArticleList(final List<Article> articles){
        final ArrayList<String> list = new ArrayList<String>();

        for(int i=0;i<articles.size();i++){
            Article article = articles.get(i);
            list.add(article.getQuestion());
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        articlelistview.setAdapter(adapter);

        articlelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article article = articles.get(position);
                changeToDetailActivity(article);
            }
        });
    }

    public void changeToDetailActivity(Article article){
        Intent intent = new Intent();

        intent.putExtra("currentArticleId", article.getArticle_id());

        intent.setClass(this,DetailActivity.class);
        startActivity(intent);
    }


}
