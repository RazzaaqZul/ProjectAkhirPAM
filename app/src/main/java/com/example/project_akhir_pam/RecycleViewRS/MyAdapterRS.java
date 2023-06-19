package com.example.project_akhir_pam.RecycleViewRS;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_akhir_pam.R;
import com.example.project_akhir_pam.RecycleView.MyViewHolder;
import com.example.project_akhir_pam.model.FuncFact;
import com.example.project_akhir_pam.model.RumahSakit;

import java.util.List;

public class MyAdapterRS extends RecyclerView.Adapter<MyViewHolderRS>{

    Fragment fragment;
    List<RumahSakit> rumahSakits;

    public MyAdapterRS(Fragment fragment, List<RumahSakit> rumahSakits) {
        this.fragment =fragment;
        this.rumahSakits= rumahSakits;
    }


    @NonNull
    @Override
    public MyViewHolderRS onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderRS(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rumah_sakit_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderRS holder, int position) {
        final RumahSakit data = rumahSakits.get(position);
        holder.tvNamaRS.setText(data.getNamaRS());
        holder.tvAlamat.setText(data.getAlamat());
        holder.tvNomorTelepon.setText(data.getNoTeleponRS());
        holder.ivLogoRS.setImageResource(data.getLogoRS());
        holder.ibPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + data.getNoTeleponRS()));  // Ganti dengan nomor telepon yang ingin Anda panggil
                fragment.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return rumahSakits.size();
    }
}
