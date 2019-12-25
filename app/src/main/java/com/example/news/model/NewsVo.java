package com.example.news.model;

public class NewsVo {

    private  String title;
    private  String link;
    private  String pubDate;
    private  String author;
    private  String description;
    String imglink;
    public void setImglink(String imglink) {
        this.imglink = imglink;
    }

    @Override
    public String toString() {
        return "NewsVo{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", imglink='" + imglink + '\'' +
                ", type=" + type +
                ", CRTIME='" + CRTIME + '\'' +
                ", doctitle='" + doctitle + '\'' +
                ", DOCID='" + DOCID + '\'' +
                ", CANSORT='" + CANSORT + '\'' +
                ", DOCPUBURL='" + DOCPUBURL + '\'' +
                ", VIEWDOCUMENT='" + VIEWDOCUMENT + '\'' +
                ", DOCOUTUPID='" + DOCOUTUPID + '\'' +
                ", CHNLDOCCRUSER='" + CHNLDOCCRUSER + '\'' +
                ", DOCSTATUS='" + DOCSTATUS + '\'' +
                ", RECID='" + RECID + '\'' +
                ", DOCCHANNEL='" + DOCCHANNEL + '\'' +
                ", ID='" + ID + '\'' +
                '}';
    }

    public String getImglink() {
        return imglink;
    }


    public String getDoctitle() {
        return doctitle;
    }

    public void setDoctitle(String doctitle) {
        this.doctitle = doctitle;
    }

    private int type;

    public String getCRTIME() {
        return CRTIME;
    }

    public void setCRTIME(String CRTIME) {
        this.CRTIME = CRTIME;
    }



    public String getDOCID() {
        return DOCID;
    }

    public void setDOCID(String DOCID) {
        this.DOCID = DOCID;
    }

    private  String  CRTIME;

    public String getCANSORT() {
        return CANSORT;
    }



    public void setCANSORT(String CANSORT) {
        this.CANSORT = CANSORT;
    }

    public String getVIEWDOCUMENT() {
        return VIEWDOCUMENT;
    }

    public void setVIEWDOCUMENT(String VIEWDOCUMENT) {
        this.VIEWDOCUMENT = VIEWDOCUMENT;
    }

    public String getDOCOUTUPID() {
        return DOCOUTUPID;
    }

    public void setDOCOUTUPID(String DOCOUTUPID) {
        this.DOCOUTUPID = DOCOUTUPID;
    }

    public String getCHNLDOCCRUSER() {
        return CHNLDOCCRUSER;
    }

    public void setCHNLDOCCRUSER(String CHNLDOCCRUSER) {
        this.CHNLDOCCRUSER = CHNLDOCCRUSER;
    }

    public String getDOCSTATUS() {
        return DOCSTATUS;
    }

    public void setDOCSTATUS(String DOCSTATUS) {
        this.DOCSTATUS = DOCSTATUS;
    }

    public String getRECID() {
        return RECID;
    }

    public void setRECID(String RECID) {
        this.RECID = RECID;
    }

    public String getDOCCHANNEL() {
        return DOCCHANNEL;
    }

    public void setDOCCHANNEL(String DOCCHANNEL) {
        this.DOCCHANNEL = DOCCHANNEL;
    }

    public String getDOCPUBURL() {
        return DOCPUBURL;
    }

    public void setDOCPUBURL(String DOCPUBURL) {
        this.DOCPUBURL = DOCPUBURL;
    }

    private  String doctitle;
     private  String DOCID;
    private  String  CANSORT;
    private  String DOCPUBURL;

    public String getID() {
        return ID;
    }
    public String getDocid() {
        return DOCID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    private  String  VIEWDOCUMENT;
    private  String  DOCOUTUPID;
    private  String  CHNLDOCCRUSER;
    private  String  DOCSTATUS;
    private  String  RECID;
    private  String  DOCCHANNEL;
    private  String  ID;



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
