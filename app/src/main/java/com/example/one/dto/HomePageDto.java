package com.example.one.dto;

import  java.util.*;

/**
 * Created by Administrator on 2015/1/9.
 */
public class HomePageDto {
    private int id;
    private Date publishDate;
    private String imgUrl;
    private String imgName;
    private String imgAuthor;
    private String oneWord;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgAuthor() {
        return imgAuthor;
    }

    public void setImgAuthor(String imgAuthor) {
        this.imgAuthor = imgAuthor;
    }

    public String getOneWord() {
        return oneWord;
    }

    public void setOneWord(String oneWord) {
        this.oneWord = oneWord;
    }
}
