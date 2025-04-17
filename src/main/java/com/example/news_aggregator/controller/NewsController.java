package com.example.news_aggregator.controller;

import com.example.news_aggregator.model.Article;
import com.example.news_aggregator.service.GuardianApiService;
import com.example.news_aggregator.service.NewsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class NewsController {

    private final NewsApiService newsApiService;
    private final GuardianApiService guardianApiService;

    @Autowired
    public NewsController(NewsApiService newsApiService,GuardianApiService guardianApiService)
    {
        this.newsApiService = newsApiService;
        this.guardianApiService = guardianApiService;
    }

    @GetMapping("/headlines")
    public List<Article> getHeadlines(@RequestParam(defaultValue = "gb")String country)
    {
        return  newsApiService.getTopHeadlines(country);
    }
    @GetMapping("/search")
    public List<Article> searchNews(@RequestParam String query)
    {
        List<Article> newsApiResults = newsApiService.searchNews(query);
        List<Article> guardianApiResults = guardianApiService.searchArticles(query);
        return Stream.concat(newsApiResults.stream(), guardianApiResults.stream())
                .sorted(Comparator.comparing(Article::getPublishedAt).reversed())
                .collect(Collectors.toList());
    }
}
