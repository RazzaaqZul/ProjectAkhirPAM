package com.example.project_akhir_pam.RecycleView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_akhir_pam.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tvDisplayJudul, tvDisplayDeskripsi;
    ConstraintLayout cardView;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        tvDisplayJudul = itemView.findViewById(R.id.tv_DisplayJudul);
        tvDisplayDeskripsi = itemView.findViewById(R.id.tv_DisplayDeskripsi);
        cardView = itemView.findViewById(R.id.card_view);
    }

}


