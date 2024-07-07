package com.example.backendfiveflowers.service;

import com.example.backendfiveflowers.entity.Blog;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogCrawlingService {

    @Autowired
    private BlogService blogService;

    public void crawlBlogPosts(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements articles = doc.select("article");

        List<Blog> posts = new ArrayList<>();

        for (Element article : articles) {
            String title = article.select("h2").text();
            String content = article.select("div.content").text();
            Blog post = new Blog();
            post.setTitle(title);
            post.setContent(content);
            posts.add(post);
        }

        for (Blog post : posts) {
            blogService.addBlog(post);
        }
    }
}
