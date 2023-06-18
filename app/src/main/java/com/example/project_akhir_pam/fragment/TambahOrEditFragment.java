package com.example.project_akhir_pam.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project_akhir_pam.R;
import com.example.project_akhir_pam.model.FuncFact;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TambahOrEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TambahOrEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    =================================== BUAT SENDIRI ========================
    private View layout;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private static final String keyTitle = "title";
    private static final String keyDescription = "description";
    private static final String keyTanggal = "tanggal";
    private static final String keyPenulis = "penulis";
    private static final String keyKey = "key";
    private String title, description, tanggal, penulis, key;

    private boolean isEdit = false;




//    =================================== END =================================

    public TambahOrEditFragment() {
        // Required empty public constructor
    }


    public static TambahOrEditFragment newInstance ( String title, String description, String tanggal, String penulis, String key) {
        TambahOrEditFragment fragment= new TambahOrEditFragment();
        Bundle simpan = new Bundle();
        simpan.putString(keyKey, key);
        simpan.putString(keyPenulis, penulis);
        simpan.putString(keyTanggal, tanggal);
        simpan.putString(keyDescription, description);
        simpan.putString(keyTitle, title);
        fragment.setArguments(simpan);
        return fragment;

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TambahOrEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TambahOrEditFragment newInstance(String param1, String param2) {
        TambahOrEditFragment fragment = new TambahOrEditFragment();
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

            title = getArguments().getString(keyTitle);
            description = getArguments().getString(keyDescription);
            tanggal = getArguments().getString(keyTanggal);
            penulis = getArguments().getString(keyPenulis);
            key = getArguments().getString(keyKey);

            if (key != null ) {
                isEdit = true;
            }

        }




    }

    EditText etAddTitle, etAddPenulis, etAddTanggalUpload, etAddDeskripsi;
    Button btnSimpan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.layout = inflater.inflate(R.layout.fragment_tambah_or_edit, container, false);
        final View view = inflater.inflate(R.layout.fragment_tambah_or_edit,container,false);
        // Inflate the layout for this fragment

        etAddTitle = this.layout.findViewById(R.id.et_AddTitle);
        etAddPenulis = this.layout.findViewById(R.id.et_AddPenulis);
        etAddTanggalUpload = this.layout.findViewById(R.id.et_AddTanggalUpload);
        etAddDeskripsi = this.layout.findViewById(R.id.et_AddDeskripsi);

        etAddTitle.setText(title);
        etAddPenulis.setText(penulis);
        etAddTanggalUpload.setText(tanggal);
        etAddDeskripsi.setText(description);

        btnSimpan = this.layout.findViewById(R.id.btn_Simpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( isEdit == false){
                    tambahData(view);
                } else if ( isEdit == true ) {
                    updateData(view);
                }

            }
        });



        firebaseDatabase= FirebaseDatabase.getInstance("https://pam-project-akhir-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference();


        return this.layout;
    }



    private void tambahData(View view) {
        System.out.println("Ini adalah pesan log.");
        String title = etAddTitle.getText().toString();
        String penulis = etAddPenulis.getText().toString();
        String tanggalUpload = etAddTanggalUpload.getText().toString();
        String deskripsi = etAddDeskripsi.getText().toString();
        FuncFact baru = new FuncFact(title, deskripsi, penulis, tanggalUpload);
        databaseReference.child("dataFunFact").push().setValue(baru).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(view.getContext(), "Berhasil di Tambahkan", Toast.LENGTH_SHORT).show();
                JelajahiFragment fragment = new JelajahiFragment();
                replacedFragment(fragment);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("gagal");
                Toast.makeText(view.getContext(), "Failed to Add data", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public  void updateData (View view) {
        //                Mengambil data/String dari EditText yang telah diisi oleh Constructor sebelumnya
        String title = etAddTitle.getText().toString();
        String penulis = etAddPenulis.getText().toString();
        String tanggalUpload = etAddTanggalUpload.getText().toString();
        String deskripsi = etAddDeskripsi.getText().toString();
        System.out.println("Masuk nih dan Kodenya adalah " + key);
        FuncFact baru = new FuncFact(title, deskripsi, tanggalUpload, penulis );
        databaseReference.child("dataFunFact").child(key).setValue(baru).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(view.getContext(), "Berhasil di Updated", Toast.LENGTH_SHORT).show();
                JelajahiFragment fragment = new JelajahiFragment();
                isEdit = false;
                replacedFragment(fragment);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(view.getContext(),"Gagal Mengupdated", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void replacedFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_2, fragment);
        fragmentTransaction.commit();
    }

}