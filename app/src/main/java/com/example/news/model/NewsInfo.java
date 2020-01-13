package com.example.news.model;



import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "NewsInfo")
public class NewsInfo {


    @Column(name = "id", isId = true, autoGen = false)
    private String id;

    @Column(name = "title")
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "time")
    private String time;

    @Column(name = "url")
    private String url;
    @Column(name = "type")
    private String type;

    public NewsInfo(String news_title, String news_url, String news_date,String news_docid,String news_type) {
        this.title=news_title;
        this.time =news_date;
        this.url= news_url;
        this.id= news_docid;
        this.type= news_type;
    }




}
