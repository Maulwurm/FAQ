package com.example.marco.faq.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Marco on 09.10.2017.
 */

public class Article {
    @SerializedName("category_id") private String category_id;
    @SerializedName("category_title") private String category_title;
    @SerializedName("article_id") private String article_id;
    @SerializedName("question") private String question;
    @SerializedName("answer") private String answer;
    @SerializedName("rating_average") private String rating_average;
    @SerializedName("rating_count") private String rating_count;

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getRating_average() {
        return rating_average;
    }

    public void setRating_average(String rating_average) {
        this.rating_average = rating_average;
    }

    public String getRating_count() {
        return rating_count;
    }

    public void setRating_count(String rating_count) {
        this.rating_count = rating_count;
    }
}
