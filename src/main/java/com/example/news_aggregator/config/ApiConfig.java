package com.example.news_aggregator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {
    @Value("${newsapi.key}")
    private String newsApiKey;

    public String getNewsApiKey()
    {
        return newsApiKey;
    }
}
