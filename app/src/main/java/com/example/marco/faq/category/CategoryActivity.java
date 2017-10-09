package com.example.marco.faq.category;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marco.faq.R;
import com.example.marco.faq.article.ArticleActivity;
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
 * Show all category element as a list
 *
 * 1. webrequest ausführen
 * 2. respone verarbeiten
 * 3. Kategorien darstellen
 */
public class CategoryActivity extends AppCompatActivity implements Callback {

    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        listview = (ListView) findViewById(R.id.listview);

        WebCommunication webCommunication = new WebCommunication();

        String url = "https://public-supportcenter-cbw.lyoness.com/de-AT/rest/faq/categories";

        webCommunication.performRequest(url, this);
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {


        ResponseBody responseBody = response.body();

        if (responseBody != null) {
            String responseString = responseBody.string();
            Logger.json(responseString);

            Gson gson = new Gson();


            //gson.fromJson(responseString,Category.class);

            //Type listType = new TypeToken<List<String>>() {}.getType();


            //List<String> yourList = new Gson().fromJson(yourJson, listType);

            Type listType = new TypeToken<List<Category>>() {}.getType();

            final List<Category> categories = gson.fromJson(responseString, listType);
            for(int i=0; i<categories.size();i++){
                Category category = categories.get(i);
                Logger.d("Id: %s Title: %s", category.getCategory_id(), category.getCategory_title());

            }

            Handler mainHandler = new Handler(getMainLooper());
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    showList(categories);
                }
            };
            mainHandler.post(runnable);

        }
    }

    public void showList(final List<Category> categories){
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < categories.size(); ++i) {
            Category category = categories.get(i);
            list.add(category.getCategory_title());
        }
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = categories.get(position);
                changeToArticleActivity(category);
            }
        });

    }

    public void changeToArticleActivity(Category category){
        Intent intent = new Intent();

        intent.putExtra("currentCategoryId", category.getCategory_id());

        intent.setClass(this, ArticleActivity.class);
        startActivity(intent);
    }
}
