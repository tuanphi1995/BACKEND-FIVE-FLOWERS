package com.example.backendfiveflowers.model;

import java.util.List;

public class NewsResponse {
    private List<Article> articles;

    // Getters và Setters
    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
