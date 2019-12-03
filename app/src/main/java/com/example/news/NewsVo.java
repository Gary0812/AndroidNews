package com.example.news;

public class NewsVo {

    private  String title;
    private  String link;
    private  String pubDate;
    private  String author;
    private  String description;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "NewsVo{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }

/*public  NewsVo (String title,String link,String pubDate,String author,String description)
{
    this.title=title;
    this.link= link;
    this.pubDate=pubDate;
    this.author=author;
    this.description=description;
}*/

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
