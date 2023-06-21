package com.example.project_akhir_pam.RecycleView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.project_akhir_pam.fragment.DetailFragment;
import com.example.project_akhir_pam.model.FuncFact;
import com.example.project_akhir_pam.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

   Fragment fragment;
    List<FuncFact> funcFacts;

    Context context;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private boolean isHome = false;

    public void setFilteredFuncFacts ( List<FuncFact> filteredFuncFacts) {
        this.funcFacts = filteredFuncFacts;
        isHome = true;
        notifyDataSetChanged();
    }

    public MyAdapter(Fragment fragment, List<FuncFact> funcFacts) {
        this.fragment =fragment;
        this.funcFacts = funcFacts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_funfact_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final FuncFact data = funcFacts.get(position);
        holder.tvDisplayDeskripsi.setText(data.getDescription());
        holder.tvDisplayJudul.setText(data.getTitle());
        Glide.with(fragment)
                .load(data.getAvatar())
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.DisplayBackground.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Reset background jika diperlukan
                        holder.DisplayBackground.setBackground(null);
                    }
                });


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !isHome ){
                    FragmentManager manager = fragment.requireFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();

                    DetailFragment fragment = DetailFragment.newInstance(
                            data.getTitle(),
                            data.getDescription(),
                            data.getTanggal(),
                            data.getPenulis(),
                            data.getKey(),
                            data.getAvatar()
                    );

                    transaction.replace(R.id.frameLayout_2, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    String url = data.getPenulis();


                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    fragment.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return funcFacts.size();
    }
}
