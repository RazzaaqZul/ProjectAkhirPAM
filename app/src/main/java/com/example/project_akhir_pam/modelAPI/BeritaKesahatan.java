package com.example.project_akhir_pam.modelAPI;

import com.google.gson.annotations.SerializedName;

public class BeritaKesahatan {

    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    @SerializedName("publishedAt")
    private String publishedAt;

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

}
