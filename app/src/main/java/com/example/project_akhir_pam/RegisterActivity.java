package com.example.project_akhir_pam;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_akhir_pam.model.FuncFact;
import com.example.project_akhir_pam.model.InformasiUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etPassword, etEmail, etUsername, etCity, etPhoneNumber;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    FuncFact funcFact;
    private FirebaseAuth mAuth;
    private Button btnSignUp;
    InformasiUser informasiUser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();


        etEmail = findViewById(R.id.et_Email);
        etPassword = findViewById(R.id.et_Password);
        etUsername = findViewById(R.id.et_Username);
        etCity = findViewById(R.id.et_Username);
        etPhoneNumber = findViewById(R.id.et_PhoneNumber);

        btnSignUp = findViewById(R.id.btn_SignUp);


        btnSignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_SignUp) {
            signUp(etEmail.getText().toString(), etPassword.getText().toString());

        }
    }
    public void submitDataRegister(){
        firebaseDatabase= FirebaseDatabase.getInstance("https://pam-project-akhir-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference();

        System.out.println("Ini adalah pesan log.");
        String username = etUsername.getText().toString();
        String city = etCity.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        InformasiUser baru = new InformasiUser(username, city, phoneNumber);
        databaseReference.child("data").child(mAuth.getUid()).push().setValue(baru).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                System.out.println("Masuk nih data profilenyaaaaa");
                Toast.makeText(RegisterActivity.this, "Add data",
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("gagal");
                Toast.makeText(RegisterActivity.this, "Failed to Add data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void signUp(String email, String password ) {
        System.out.println(email);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            submitDataRegister();
//                            Sign In success, update UI with the signed-in user's information
                            System.out.println("masuk nih registernya");
                            Toast.makeText(RegisterActivity.this, "Asikk ke register",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user
                            Log.w(TAG, "createUserWithEmail:failure",
                                    task.getException());
                            Toast.makeText(RegisterActivity.this,
                                    "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser user) {
        if (user != null ){
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(RegisterActivity.this, "Register in First", Toast.LENGTH_SHORT).show();
        }
    }

    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }




}
