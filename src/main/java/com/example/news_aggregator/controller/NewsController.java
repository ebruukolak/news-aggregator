package com.example.news_aggregator.controller;

import com.example.news_aggregator.model.Article;
import com.example.news_aggregator.service.NewsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class NewsController {

    private final NewsApiService newsApiService;

    @Autowired
    public NewsController(NewsApiService newsApiService)
    {
        this.newsApiService = newsApiService;
    }

    @GetMapping("/headlines")
    public List<Article> getHeadlines(@RequestParam(defaultValue = "gb")String country)
    {
        return  newsApiService.getTopHeadlines(country);
    }
    @GetMapping("/search")
    public List<Article> searchNews(@RequestParam String query)
    {
        return newsApiService.searchNews(query);
    }
}
