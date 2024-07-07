package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Blog;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NewsSearchService {

    private static final String SUBSCRIPTION_KEY = "YOUR_BING_SEARCH_V7_SUBSCRIPTION_KEY";
    private static final String SEARCH_URL = "https://api.cognitive.microsoft.com/bing/v7.0/news/search";

    @Autowired
    private BlogService blogService;

    public void searchAndSaveNews(String query) throws IOException {
        List<Blog> posts = searchNews(query);

        for (Blog post : posts) {
            blogService.addBlog(post);
        }
    }

    private List<Blog> searchNews(String query) throws IOException {
        List<Blog> blogPosts = new ArrayList<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(SEARCH_URL + "?q=" + query + "&count=10");

        request.setHeader("Ocp-Apim-Subscription-Key", SUBSCRIPTION_KEY);

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String jsonResponse = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray articles = jsonObject.getJSONArray("value");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = article.getString("name");
                String content = article.getString("description");
                String url = article.getString("url");

                Blog blogPost = new Blog();
                blogPost.setTitle(title);
                blogPost.setContent(content + " Read more at: " + url);
                blogPosts.add(blogPost);
            }
        }

        return blogPosts;
    }
}
