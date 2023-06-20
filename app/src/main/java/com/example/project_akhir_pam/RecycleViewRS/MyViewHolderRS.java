package com.example.project_akhir_pam.RecycleViewRS;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_akhir_pam.R;

public class MyViewHolderRS extends RecyclerView.ViewHolder {

    ImageView ivLogoRS;
    TextView tvNamaRS, tvAlamat, tvNomorTelepon, tvJamBuka;
    ImageButton ibPhone;

    public MyViewHolderRS(@NonNull View itemView) {
        super(itemView);

//        tvDisplayJudul = itemView.findViewById(R.id.tv_DisplayJudul);
//        tvDisplayDeskripsi = itemView.findViewById(R.id.tv_DisplayDeskripsi);
//        cardView = itemView.findViewById(R.id.card_view);
        ivLogoRS = itemView.findViewById(R.id.iv_LogoRS);
        tvNamaRS = itemView.findViewById(R.id.tv_NamaRS);
        tvAlamat = itemView.findViewById(R.id.tv_Alamat);
        tvNomorTelepon = itemView.findViewById(R.id.tv_NomorTelepon);
        tvJamBuka = itemView.findViewById(R.id.tv_JamBuka);
        ibPhone = itemView.findViewById(R.id.ib_Phone);


    }
}
