package com.news.bbcnewsreader.dto;

import java.util.ArrayList;

public class BBCChannel {

    private String title;
    private String description;
    private String link;
    private String generator;
    private String lastBuildDate;
    private String copyright;
    private String language;
    private String ttl;
    private BBCImage image;
    private ArrayList<BBCEntry> entries;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public BBCImage getImage() {
        return image;
    }

    public void setImage(BBCImage image) {
        this.image = image;
    }

    public ArrayList<BBCEntry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<BBCEntry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return "BBCChannel{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", generator='" + generator + '\'' +
                ", lastBuildDate='" + lastBuildDate + '\'' +
                ", copyright='" + copyright + '\'' +
                ", language='" + language + '\'' +
                ", ttl='" + ttl + '\'' +
                ", image=" + image +
                ", entries=" + entries +
                '}';
    }
}
