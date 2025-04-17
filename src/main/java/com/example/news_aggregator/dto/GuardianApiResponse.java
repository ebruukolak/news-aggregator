package com.example.news_aggregator.dto;

import java.util.List;

public class GuardianApiResponse {
    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    private Response response;

    public static class Response {
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }

        private String status;
        private int total;
        private List<Result> results;
    }

    public static class Result {
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public String getWebTitle() {
            return webTitle;
        }

        public void setWebTitle(String webTitle) {
            this.webTitle = webTitle;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        public String getWebPublicationDate() {
            return webPublicationDate;
        }

        public void setWebPublicationDate(String webPublicationDate) {
            this.webPublicationDate = webPublicationDate;
        }

        private String id;
        private String type;
        private String sectionName;
        private String webTitle;
        private String webUrl;
        private String webPublicationDate;
    }
}
