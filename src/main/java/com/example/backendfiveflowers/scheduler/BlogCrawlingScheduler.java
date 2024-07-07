package com.example.backendfiveflowers.scheduler;

import com.example.backendfiveflowers.service.NewsSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BlogCrawlingScheduler {

    @Autowired
    private NewsSearchService newsSearchService;

    @Scheduled(cron = "0 0 0 * * ?") // Chạy hàng ngày vào nửa đêm
    public void scheduleBlogCrawling() {
        String query = "bicycle news"; // Từ khóa tìm kiếm
        try {
            newsSearchService.searchAndSaveNews(query);
        } catch (IOException e) {
            // Xử lý ngoại lệ
            System.err.println("Error during scheduled news search: " + e.getMessage());
        }
    }
}
