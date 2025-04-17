package com.example.news_aggregator.service;

import com.example.news_aggregator.config.ApiConfig;
import com.example.news_aggregator.dto.GuardianApiResponse;
import com.example.news_aggregator.dto.NewsApiResponse;
import com.example.news_aggregator.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class GuardianApiService {

    private final RestTemplate restTemplate;
    private final ApiConfig apiConfig;

    @Autowired
    public GuardianApiService(ApiConfig apiConfig){
        this.restTemplate = new RestTemplate();
        this.apiConfig = apiConfig;
    }

    public List<Article> searchArticles(String query) {

        String url = "https://content.guardianapis.com/search?q=" +query+ "&api-key=" + apiConfig.getGuardianApiKey();

        try {
            var response = restTemplate.getForObject(url, GuardianApiResponse.class);
            if (response != null) {
                if ("ok".equals(response.getResponse().getStatus())) {
                    return convertToArticles(response);
                } else {
                    System.err.println("API Error: " + response.getResponse().getStatus());
                }
            }
        } catch (Exception e) {
            System.err.println("Exception while calling News API: " + e.getMessage());
        }

        return new ArrayList<>();

    }

    private List<Article> convertToArticles(GuardianApiResponse response) {
        List<Article> articles = new ArrayList<>();

        response.getResponse().getResults().forEach( guardianApiResult -> {
            Article article = new Article();
            article.setTitle(guardianApiResult.getWebTitle());
            article.setDescription(guardianApiResult.getSectionName());
            article.setUrl(guardianApiResult.getWebUrl());
            article.setContent(guardianApiResult.getType());



            try{
                if(guardianApiResult.getWebPublicationDate()!=null)
                {
                    ZonedDateTime zonedDateTime = ZonedDateTime.parse(
                            guardianApiResult.getWebPublicationDate(),
                            DateTimeFormatter.ISO_DATE_TIME
                    );
                    article.setPublishedAt(zonedDateTime.toLocalDateTime());

                }
            }
            catch (Exception ex){
                article.setPublishedAt(LocalDateTime.now());
            }
            articles.add(article);
        });

        return articles;
    }

}
