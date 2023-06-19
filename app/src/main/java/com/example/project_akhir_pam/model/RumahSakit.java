package com.example.project_akhir_pam.model;

public class RumahSakit {
    String namaRS, alamat, noTeleponRS, jamBuka;
    private int logoRS;

    public RumahSakit(String namaRS, String alamat, String noTeleponRS, String jamBuka, int logoRs) {
        this.namaRS = namaRS;
        this.alamat = alamat;
        this.noTeleponRS = noTeleponRS;
        this.jamBuka = jamBuka;
        this.logoRS = logoRs;
    }

    public int getLogoRS() {
        return logoRS;
    }

    public void setLogoRS(int logoRS) {
        this.logoRS = logoRS;
    }

    public String getNamaRS() {
        return namaRS;
    }

    public void setNamaRS(String namaRS) {
        this.namaRS = namaRS;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTeleponRS() {
        return noTeleponRS;
    }

    public void setNoTeleponRS(String noTeleponRS) {
        this.noTeleponRS = noTeleponRS;
    }

    public String getJamBuka() {
        return jamBuka;
    }

    public void setJamBuka(String jamBuka) {
        this.jamBuka = jamBuka;
    }
}
