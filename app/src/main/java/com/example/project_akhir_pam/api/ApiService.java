package com.example.project_akhir_pam.api;

import com.example.project_akhir_pam.modelAPI.BeritaKesahatan;
import com.example.project_akhir_pam.modelAPI.BeritaKesehatanResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("top-headlines?country=id&category=health&apiKey=afb66cb11b7d45729907d6926c4920b3")
    Call<BeritaKesehatanResponse>getNews();
}