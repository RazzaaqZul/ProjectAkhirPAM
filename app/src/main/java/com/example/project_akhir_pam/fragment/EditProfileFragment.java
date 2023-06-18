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
import com.example.project_akhir_pam.model.InformasiUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


//   ============================= BUATAN SENDIRI =============================================

    private View layout;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private InformasiUser informasiUser;
    ArrayList<InformasiUser> list;

    private String username, city, nomorTelepon, key;

    private static final String keyUsername = "username";
    private static final String keyCity = "city";
    private static final String keyNomorTelepon = "nomorTelepon";
    private static final String keyKey = "key";


//    ============================= END BUATAN SENDIRI ==========================================

    public EditProfileFragment() {
        // Required empty public constructor
    }

    public static EditProfileFragment newInstance ( String username, String city, String nomorTelepon, String key) {
        EditProfileFragment fragment= new EditProfileFragment();
        Bundle simpan = new Bundle();
        simpan.putString(keyKey, key);
        simpan.putString(keyUsername, username);
        simpan.putString(keyCity, city);
        simpan.putString(keyNomorTelepon, nomorTelepon);
        fragment.setArguments(simpan);
        return fragment;

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
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

            key = getArguments().getString(keyKey);
            username = getArguments().getString(keyUsername);
            city = getArguments().getString(keyCity);
            nomorTelepon = getArguments().getString(keyNomorTelepon);

        }
    }

    EditText etUsernameProfile, etCityProfile, etNomorTeleponProfile;
    Button btnSaveProfile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.layout = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        etUsernameProfile = this.layout.findViewById(R.id.et_UsernameProfile);
        etCityProfile = this.layout.findViewById(R.id.et_CityProfile);
        etNomorTeleponProfile = this.layout.findViewById(R.id.et_NomorTeleponProfile);

        etUsernameProfile.setText(username);
        etCityProfile.setText(city);
        etNomorTeleponProfile.setText(nomorTelepon);

        firebaseDatabase= FirebaseDatabase.getInstance("https://pam-project-akhir-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();


        btnSaveProfile = this.layout.findViewById(R.id.btn_SaveProfile);
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                Mengambil data/String dari EditText yang telah diisi oleh Constructor sebelumnya
                String usernames = etUsernameProfile.getText().toString();
                String citys = etCityProfile.getText().toString();
                String nomorTelepons = etNomorTeleponProfile.getText().toString();
                System.out.println("Masuk nih dan Kodenya adalah " + key);
                InformasiUser baru = new InformasiUser(usernames, citys, nomorTelepons );
                databaseReference.child("data").child(mAuth.getUid()).child(key).setValue(baru).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(view.getContext(), "Berhasil di Updated", Toast.LENGTH_SHORT).show();
                        ProfileFragment fragment = new ProfileFragment();
                        replacedFragment(fragment);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(),"Gagal Mengupdated", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return this.layout;

    }

    private void replacedFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_profile, fragment);
        fragmentTransaction.commit();
    }



}