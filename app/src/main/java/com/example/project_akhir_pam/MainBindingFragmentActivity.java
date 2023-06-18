package com.example.project_akhir_pam;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_akhir_pam.fragment.HomeFragment;
import com.example.project_akhir_pam.fragment.JelajahiFragment;
import com.example.project_akhir_pam.fragment.MainJelajahFragment;
import com.example.project_akhir_pam.fragment.MainProfileFragment;
import com.example.project_akhir_pam.fragment.MapFragment;
import com.example.project_akhir_pam.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainBindingFragmentActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        replacedFragment(new HomeFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
//            home, serach, map, profile
            if ( item.getItemId() == R.id.home) {
                replacedFragment(new HomeFragment());

            } else if ( item.getItemId() == R.id.search) {
                replacedFragment(new MainJelajahFragment());
            } else if ( item.getItemId() == R.id.map) {
                replacedFragment(new MapFragment());
            } else if ( item.getItemId() == R.id.profile) {
                replacedFragment(new MainProfileFragment());
            }



            return true;

        });


    }


    private void replacedFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }


}

