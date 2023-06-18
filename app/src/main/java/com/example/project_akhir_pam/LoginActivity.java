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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etPassword, etUsername;
    private FirebaseAuth mAuth;
    private Button btnLogin;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        etUsername = findViewById(R.id.et_Email);
        etPassword = findViewById(R.id.et_Password);

        btnLogin = findViewById(R.id.btn_SignIn);

        btnLogin.setOnClickListener(this);

    }


    public void login(String email, String password){

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new
                        OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
//                                    Sign in Success, update UI with the signed-in user's information
                                    Log.d(TAG, "Sign in with email berhasil");
                                    Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
//                                    If sign in fails, display message
                                    Log.w(TAG, "Sign in with email gagal");
                                    Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            }
                        });
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_SignIn) {
            login(etUsername.getText().toString(), etPassword.getText().toString());
        }
    }


    public void updateUI(FirebaseUser user) {
        if (user != null ){
            Intent intent = new Intent(LoginActivity.this, MainBindingFragmentActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Log in First", Toast.LENGTH_SHORT).show();
        }
    }

    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }


}
