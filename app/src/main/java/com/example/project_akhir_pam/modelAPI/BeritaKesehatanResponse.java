package com.example.project_akhir_pam.modelAPI;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeritaKesehatanResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("totalResults")
    private String totalResults;

    @SerializedName("articles")
    private List<BeritaKesahatan> articles;

    public List<BeritaKesahatan> getArticles() {
        return articles;
    }

    public String getStatus() {
        return status;
    }


}
