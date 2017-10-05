package com.example.marco.faq.article;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.marco.faq.R;
import com.example.marco.faq.category.CategoryActivity;
import com.orhanobut.logger.Logger;

/**
 * Created by Marco on 05.10.2017.
 */

public class ArticleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();
        String categoryId = intent.getStringExtra("currentCategoryId");
        Logger.d(categoryId);
    }


}
