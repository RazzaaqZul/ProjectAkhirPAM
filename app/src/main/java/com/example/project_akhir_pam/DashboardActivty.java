package com.example.project_akhir_pam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_akhir_pam.model.FuncFact;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DashboardActivty extends AppCompatActivity {
//    private FirebaseDatabase firebaseDatabase;
//    private DatabaseReference databaseReference;
//
//    FuncFact funcFact;
//    private FirebaseAuth mAuth;
//    Button btnSubmit, btnLogout;
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dashboard);
//        mAuth = FirebaseAuth.getInstance();
//        btnSubmit = findViewById(R.id.btn_submit);
//        btnLogout = findViewById(R.id.btn_Logout);
//        btnSubmit.setOnClickListener(this);
//        btnLogout.setOnClickListener(this);
//
////       Cek Admin
//        if ( mAuth.getUid().equals("FMWTO2sEUsWeBnePvkMqSGfsGPE2")) {
//            btnSubmit.setVisibility(View.VISIBLE);
//        } else {
//            btnSubmit.setVisibility(View.INVISIBLE);
//        }
//
//        firebaseDatabase= FirebaseDatabase.getInstance("https://pam-project-akhir-default-rtdb.asia-southeast1.firebasedatabase.app/");
//        databaseReference = firebaseDatabase.getReference();
//
//
//    }
//
//
//    @Override
//    public void onClick(View view) {
//        if (view.getId() == R.id.btn_submit) {
////          submitData();
//            Intent intent = new Intent(DashboardActivty.this, MainBindingFragmentActivity.class);
//            startActivity(intent);
//        } else if (view.getId() == R.id.btn_Logout){
//            logOut();
//        }
//    }
//
//    public void logOut() {
//        mAuth.signOut();
//        Intent intent = new Intent(DashboardActivty.this, MainActivity.class);
////        MAKE SURE USER CANT GO BACK
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK
//        );
//        startActivity(intent);
//
//    }
//
//    public void submitData(){
//
//        System.out.println("Ini adalah pesan log.");
//        String title = "TEST!@#";
//        String desc = "INI DESKRIPSI KITA YAH";
//        FuncFact baru = new FuncFact(title, desc);
//        databaseReference.child("dataFunFact").push().setValue(baru).addOnSuccessListener(this, new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                System.out.println("Masuk nih");
//                Toast.makeText(DashboardActivty.this, "Add data",
//                        Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(this, new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                System.out.println("gagal");
//                Toast.makeText(DashboardActivty.this, "Failed to Add data", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


}
