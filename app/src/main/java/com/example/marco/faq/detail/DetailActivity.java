package com.example.marco.faq.detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marco.faq.R;
import com.example.marco.faq.models.Article;
import com.example.marco.faq.models.Detail;
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
 * Created by Marco on 09.10.2017.
 */

public class DetailActivity extends AppCompatActivity implements Callback {
    private TextView detailtextview;
    private TextView questiontextview;
    private RatingBar ratingBar;
    private TextView allratings;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailtextview = (TextView) findViewById(R.id.detailtextview);
        questiontextview = (TextView) findViewById(R.id.questiontextview);
        ratingBar = (RatingBar) findViewById(R.id.rating);
        allratings = (TextView) findViewById(R.id.allratings);

        Intent intent = getIntent();
        String articleId = intent.getStringExtra("currentArticleId");
        Logger.d("ArticleId: %s", articleId);

        WebCommunication webCommunication = new WebCommunication();

        String url = "https://public-supportcenter-cbw.lyoness.com/de-AT/rest/faq/article/" + articleId;

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

            Type listType = new TypeToken<List<Detail>>() {}.getType();


            try {

                List<Detail> details = gson.fromJson(responseString, listType);
                final Detail detail = details.get(0);

                Handler mainHandler = new Handler(getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        showDetailList(detail);
                    }
                };
                mainHandler.post(runnable);

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Deprecated
    public void showDetailList(final List<Detail> details){
        final ArrayList<String> list = new ArrayList<String>();

        for(int i=0;i<details.size();i++){
            Detail detail = details.get(i);


            questiontextview.setText(detail.getQuestion());
            detailtextview.setText(detail.getAnswer());
            ratingBar.setRating(Float.valueOf(detail.getField_rest_vote_avg()));
        }
    }

    public void showDetailList(Detail detail){
        questiontextview.setText(detail.getQuestion());
        detailtextview.setText(detail.getAnswer());
        try{
            ratingBar.setRating(Float.valueOf(detail.getField_rest_vote_avg()));
        } catch (Exception e){
            e.printStackTrace();
        }

        allratings.setText("(" + detail.getField_rest_vote_count() + ")");
    }
}
