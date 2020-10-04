package com.example.topnewsheadlines;

public class NewsInfo {
    public String image;
    public String headline;
    public String description;
    public String source;
    public String publishedAt;


    public NewsInfo(String image, String headline, String description, String source, String publishedAt) {
        this.image = image;
        this.headline = headline;
        this.description = description;
        this.source = source;
        this.publishedAt = publishedAt;
    }

    public String getImage() {
        return image;
    }

    public String getHeadline() {
        return headline;
    }

    public String getDescription() {
        return description;
    }

    public String getSource() {
        return source;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
}
