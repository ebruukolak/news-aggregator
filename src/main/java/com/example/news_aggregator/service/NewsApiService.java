package com.example.news_aggregator.service;

import com.example.news_aggregator.config.ApiConfig;
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
public class NewsApiService {
  private final RestTemplate restTemplate;
  private final ApiConfig apiConfig;

  @Autowired
  public NewsApiService(ApiConfig apiConfig){
      this.restTemplate = new RestTemplate();
      this.apiConfig = apiConfig;
  }

  public List<Article> getTopHeadlines(String country)
  {
      String url = "https://newsapi.org/v2/top-headlines?country=" +country+ "&apiKey=" + apiConfig.getNewsApiKey();

      try {
         var response = restTemplate.getForObject(url, NewsApiResponse.class);
          if (response != null) {
              if ("ok".equals(response.getStatus())) {
                  return convertToArticles(response);
              } else {
                  System.err.println("API Error: " + response.getStatus());
              }
          }
      } catch (Exception e) {
          System.err.println("Exception while calling News API: " + e.getMessage());
      }

      return new ArrayList<>();
  }

  public List<Article> searchNews(String query){
      String url = "https://newsapi.org/v2/everything?q=" + query + "&apiKey=" + apiConfig.getNewsApiKey();

      try{
          var response = restTemplate.getForObject(url,NewsApiResponse.class);
          if (response != null) {
              if ("ok".equals(response.getStatus())) {
                  return convertToArticles(response);
              } else {
                  System.err.println("API Error: " + response.getStatus());
              }
          }
      } catch (Exception e) {
          System.err.println("Exception while calling News API: " + e.getMessage());
      }

      return  new ArrayList<>();
  }

    private List<Article> convertToArticles(NewsApiResponse response) {
      List<Article> articles = new ArrayList<>();

      response.getArticles().forEach( newsApiArticle -> {
          Article article = new Article();
          article.setTitle(newsApiArticle.getTitle());
          article.setDescription(newsApiArticle.getDescription());
          article.setUrl(newsApiArticle.getUrl());
          article.setImageUrl(newsApiArticle.getUrlToImage());
          article.setContent(newsApiArticle.getContent());
          article.setAuthor(newsApiArticle.getAuthor());

          if(newsApiArticle.getSource() != null){
              article.setSource(newsApiArticle.getSource().getName());
          }

          try{
               if(newsApiArticle.getPublishedAt()!=null)
               {
                   ZonedDateTime zonedDateTime = ZonedDateTime.parse(
                                           newsApiArticle.getPublishedAt(),
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
