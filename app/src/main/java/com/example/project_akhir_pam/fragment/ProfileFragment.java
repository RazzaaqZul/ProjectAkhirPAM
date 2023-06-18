package com.example.project_akhir_pam.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.project_akhir_pam.DashboardActivty;
import com.example.project_akhir_pam.MainActivity;
import com.example.project_akhir_pam.R;
import com.example.project_akhir_pam.RecycleView.MyAdapter;
import com.example.project_akhir_pam.model.FuncFact;
import com.example.project_akhir_pam.model.InformasiUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

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
    private String city, key ;


//    ============================= END BUATAN SENDIRI ==========================================

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        tampilData();
    }

    TextView tvUsername, tvNomorTelepon;
    Button btnEditProfile, btnLogoutProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.layout = inflater.inflate(R.layout.fragment_profile, container, false);
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        mAuth = FirebaseAuth.getInstance();

        tvUsername = this.layout.findViewById(R.id.tv_Username);
        tvNomorTelepon = this.layout.findViewById(R.id.tv_NomorTelepon);


        btnEditProfile = this.layout.findViewById(R.id.btn_EditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager =  getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                EditProfileFragment fragment = EditProfileFragment.newInstance(
                        tvUsername.getText().toString(),
                        city,
                        tvNomorTelepon.getText().toString(),
                        key
                );

                transaction.replace(R.id.frameLayout_profile, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnLogoutProfile = this.layout.findViewById(R.id.btn_LogoutProfile);
        btnLogoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(view.getContext(), MainActivity.class);
//        MAKE SURE USER CANT GO BACK
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK
                );
                startActivity(intent);
            }
        });
        list = new ArrayList<InformasiUser>();
        tampilData();


        return this.layout;
    }



    private void replacedFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_profile, fragment);
        fragmentTransaction.commit();
    }


    private void tampilData() {
        firebaseDatabase= FirebaseDatabase.getInstance("https://pam-project-akhir-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference("data");
        mAuth = FirebaseAuth.getInstance();
        System.out.println("MASUK NIH");

        databaseReference.child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Menghapus list lama dan menggantikan ke data baru setelah di looping (for)
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Mengambil data dalam bentuk JSON atau objek misal  {description=aldy juga makan ayam, title=Saya Makan Ayam}
                    InformasiUser note = dataSnapshot.getValue(InformasiUser.class);
                    informasiUser = new InformasiUser(note.getUsername(), note.getCity(), note.getPhoneNumber());
//                    Mengambil Key dan Menyimpan Key untuk identfikasi Child pada Update dan Delete
                    note.setKey(dataSnapshot.getKey());
//                     Melakukan Set Data
                    tvUsername.setText(note.getUsername().toString());
                    tvNomorTelepon.setText(note.getPhoneNumber().toString());
                    city = note.getCity().toString();
                    key = note.getKey().toString();
                    System.out.println("INI UDAH BERHASIL");
                    System.out.println(note.getUsername());
                    System.out.println(note.getCity());

                }
            }

            @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


}