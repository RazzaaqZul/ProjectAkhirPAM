package com.example.project_akhir_pam.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.project_akhir_pam.model.FuncFact;
import com.example.project_akhir_pam.R;
import com.example.project_akhir_pam.RecycleView.MyAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JelajahiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JelajahiFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
//    BUATAN SENDIRI ===============================


    RecyclerView tv_tampil;
    private View layout;
    ArrayList<FuncFact> funcFacts;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    ImageView btnTambah;


    //    END BUATAN SENDIRI ============================
    public JelajahiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JelajahiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JelajahiFragment newInstance(String param1, String param2) {
        JelajahiFragment fragment = new JelajahiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.layout = inflater.inflate(R.layout.fragment_jelajahi, container, false);

        tv_tampil = this.layout.findViewById(R.id.recyclerview);
        funcFacts = new ArrayList<FuncFact>();
//        Menampilkan data Funfact dengan RecycleView
        tampilData();

//        Menambah Button untuk menambahkan Item
        btnTambah = this.layout.findViewById(R.id.btn_TambahOrEdit);
        btnTambah.setOnClickListener(this);

//        Cek Admin
        mAuth = FirebaseAuth.getInstance();
        if ( mAuth.getUid().equals("bPRlbRpOVmYWFCmt2heWMArKIal2")) {
            btnTambah.setVisibility(View.VISIBLE);
        } else {
            btnTambah.setVisibility(View.INVISIBLE);
        }

        return this.layout;
    }

    //    BUATAN SENDIRI ==================================
    private void tampilData() {
        firebaseDatabase = FirebaseDatabase.getInstance("https://pam-project-akhir-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference("dataFunFact");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Menghapus list lama dan menggantikan ke data baru setelah di looping (for)
                funcFacts.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Mengambil data dalam bentuk JSON atau objek misal  {description=aldy juga makan ayam, title=Saya Makan Ayam}
                    FuncFact postingan = dataSnapshot.getValue(FuncFact.class);
                    funcFacts.add(postingan);
//                    Mengambil Key dan Menyimpan Key untuk identfikasi Child pada Update dan Delete
                    postingan.setKey(dataSnapshot.getKey());

                }

//                Logic untuk adapter
                MyAdapter adapter = new MyAdapter(JelajahiFragment.this, funcFacts);
                tv_tampil.setLayoutManager(new LinearLayoutManager(JelajahiFragment.this.getContext()));
                tv_tampil.setAdapter(adapter);
//                Memberikan informasi bahwa adapter telah berubah
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_TambahOrEdit){
            tambahData();
        }

    }

    private void tambahData() {
        TambahOrEditFragment fragment = new TambahOrEditFragment();
        replacedFragment(fragment);
    }

    private void replacedFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_2, fragment);
        fragmentTransaction.commit();
    }


}