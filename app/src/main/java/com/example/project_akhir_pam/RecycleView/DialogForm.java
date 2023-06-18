package com.example.project_akhir_pam.RecycleView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.project_akhir_pam.R;

public class DialogForm extends DialogFragment {
    String title, description, tanggal, penulis, key;

    public DialogForm(String title, String description, String tanggal, String penulis, String key) {
        this.title = title;
        this.description = description;
        this.tanggal = tanggal;
        this.penulis = penulis;
        this.key = key;
    }

    EditText etTittle, etDescription, etPenulis, etTanggal;
    Button btnSave;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.edit_tambah_funfact,container, false);

        etTittle = view.findViewById(R.id.tv_Title);
        etDescription = view.findViewById(R.id.tv_Description);
        etPenulis = view.findViewById(R.id.tv_Penulis);
        etTanggal = view.findViewById(R.id.tv_Tanggal);

//        Mengisi data yang telah ada sebelumnya
        etTittle.setText(title);
        etDescription.setText(description);
        etPenulis.setText(penulis);
        etTanggal.setText(tanggal);




        return view;

    }
}
