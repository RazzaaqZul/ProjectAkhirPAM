package com.example.project_akhir_pam.model;

public class FuncFact {

    private String title, description, penulis, tanggal, key;
    public FuncFact(){

    }

    public FuncFact(String title, String description, String penulis, String tanggal) {
        this.title = title;
        this.description = description;
        this.penulis = penulis;
        this.tanggal = tanggal;
    }

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

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
