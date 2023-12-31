package com.example.project_akhir_pam.fragment;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_akhir_pam.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public DetailFragment() {
        // Required empty public constructor
    }
//  BUATAN SENDIRI ==============================================================================
    private View layout;
    private FirebaseAuth mAuth;
    private String title, description, tanggal, penulis, key, avatar;
    private static final String keyTitle = "title";
    private static final String keyDescription = "description";
    private static final String keyTanggal = "tanggal";
    private static final String keyPenulis = "penulis";
    private static final String keyKey = "key";
    private static final String keyAvatar = "avatar";

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;




    public static DetailFragment newInstance ( String title, String description, String tanggal, String penulis, String key, String avatar) {
        DetailFragment fragment = new DetailFragment();
        Bundle simpan = new Bundle();
        simpan.putString(keyKey, key);
        simpan.putString(keyPenulis, penulis);
        simpan.putString(keyTanggal, tanggal);
        simpan.putString(keyDescription, description);
        simpan.putString(keyTitle, title);
        simpan.putString(keyAvatar, avatar);
        fragment.setArguments(simpan);
        return fragment;

    }

    //    END BUATAN SENDIRI =======================================================================

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
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
            avatar = getArguments().getString(keyAvatar);

        }
    }

    TextView tvJudulDetail, tvPenulisDetail, tvTanggalDetail, tvDeskripsiDetail;
    ImageView ivAvatar;
    Button btnEdit, btnHapus;
    ImageButton ibDownloadImage, ibButton_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       this.layout = inflater.inflate(R.layout.fragment_detail, container, false);
       View view = inflater.inflate(R.layout.fragment_detail, container, false);

//       Update the views with the passed argument
        tvJudulDetail = this.layout.findViewById(R.id.tv_JudulDetail);
        tvPenulisDetail = this.layout.findViewById(R.id.tv_PenulisDetail);
        tvTanggalDetail = this.layout.findViewById(R.id.tv_TanggalDetail);
        tvDeskripsiDetail = this.layout.findViewById(R.id.tv_DeskripsiDetail);
        ivAvatar = this.layout.findViewById(R.id.ib_button_back_profile);
        // Menggunakan Glide untuk memuat gambar ke ImageView
        Glide.with(DetailFragment.this)
                .load(avatar)
                .into(ivAvatar);

        btnEdit = this.layout.findViewById(R.id.btn_Edit);
        btnHapus = this.layout.findViewById(R.id.btn_Hapus);
        ibDownloadImage = this.layout.findViewById(R.id.ib_downloadImage);
        ibButton_back = this.layout.findViewById(R.id.ib_button_back);

        tvJudulDetail.setText(title);
        tvPenulisDetail.setText(penulis);
        tvTanggalDetail.setText(tanggal);
        tvDeskripsiDetail.setText(description);

        firebaseDatabase= FirebaseDatabase.getInstance("https://pam-project-akhir-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference();


        System.out.println("INI ADALAH LINK AVATAR");
        System.out.println(avatar);
        System.out.println("=======================================");


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                System.out.println("INI UDAH KE KLIK");
                TambahOrEditFragment fragment = TambahOrEditFragment.newInstance(
                       tvJudulDetail.getText().toString(),
                        tvDeskripsiDetail.getText().toString(),
                        tvTanggalDetail.getText().toString(),
                        tvPenulisDetail.getText().toString(),
                        key,
                        avatar
                );

                transaction.replace(R.id.frameLayout_2, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        data.getKey() didapatkan dari NoteActivity.class pada method tampilData() ke OnDataChange()
                        databaseReference.child("dataFunFact").child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(view.getContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                                JelajahiFragment fragment = new JelajahiFragment();
                                replacedFragment(fragment);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(), "Gagal Mengapus Data", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setMessage("Apakah anda yakin menghapus ? " + title );
                builder.show();
            }
        });

        ibDownloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImage();
            }
        });

        ibButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JelajahiFragment fragment = new JelajahiFragment();
                replacedFragment(fragment);
            }
        });
//        Cek Admin
        mAuth = FirebaseAuth.getInstance();
        if ( mAuth.getUid().equals("bPRlbRpOVmYWFCmt2heWMArKIal2")) {
            btnEdit.setVisibility(View.VISIBLE);
            btnHapus.setVisibility(View.VISIBLE);
        } else {
            btnEdit.setVisibility(View.INVISIBLE);
            btnHapus.setVisibility(View.INVISIBLE);
        }

       return this.layout;

    }


    private void replacedFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout_2, fragment);
        fragmentTransaction.commit();
    }
    private void downloadImage() {
        DownloadManager downloadManager = (DownloadManager) requireContext().getSystemService(getContext().DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(avatar);

        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(requireContext(), Environment.DIRECTORY_DOWNLOADS, getPathFromUrl(avatar));

        downloadManager.enqueue(request);
    }

    private String getPathFromUrl(String url) {
        String[] segments = url.split("/");
        return segments[segments.length - 1];
    }


}