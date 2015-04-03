package com.example.one.dto;

import java.util.*;
/**
 * Created by Administrator on 2015/1/9.
 */
public class ArticleDto {
    private int id;
    private Date publishDate;
    private String articleTitle;
    private String articleAuthor;
    private String articleAuthorDesc;
    private String article;

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

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }

    public String getArticleAuthorDesc() {
        return articleAuthorDesc;
    }

    public void setArticleAuthorDesc(String articleAuthorDesc) {
        this.articleAuthorDesc = articleAuthorDesc;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }
}
