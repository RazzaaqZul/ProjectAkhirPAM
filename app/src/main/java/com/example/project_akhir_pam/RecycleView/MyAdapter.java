package com.example.project_akhir_pam.RecycleView;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


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
                            data.getKey()
                    );

                    transaction.replace(R.id.frameLayout_2, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return funcFacts.size();
    }
}
