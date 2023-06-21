package com.example.project_akhir_pam.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_akhir_pam.R;
import com.example.project_akhir_pam.model.FuncFact;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

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
    private static final String keyAvatar = "avatar";

    private String title, description, tanggal, penulis, key, avatar;

    private boolean isEdit = false;


    private ProgressDialog progressDialog;




//    =================================== END =================================

    public TambahOrEditFragment() {
        // Required empty public constructor
    }


    public static TambahOrEditFragment newInstance ( String title, String description, String tanggal, String penulis, String key, String avatar) {
        TambahOrEditFragment fragment= new TambahOrEditFragment();
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
            avatar = getArguments().getString(keyAvatar);

            if (key != null ) {
                isEdit = true;
            }

        }




    }

    EditText etAddTitle, etAddPenulis, etAddTanggalUpload, etAddDeskripsi;
    ImageButton ibImageUpload;
    Button btnSimpan;
    ImageView iv_Avatar;
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
        iv_Avatar = this.layout.findViewById(R.id.iv_Avatar);
        ibImageUpload = this.layout.findViewById(R.id.ib_UploadImage);

        ibImageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        // Menggunakan Glide untuk memuat gambar ke ImageView
        Glide.with(TambahOrEditFragment.this)
                .load(avatar)
                .into(iv_Avatar);


        etAddTitle.setText(title);
        etAddPenulis.setText(penulis);
        etAddTanggalUpload.setText(tanggal);
        etAddDeskripsi.setText(description);

        btnSimpan = this.layout.findViewById(R.id.btn_Simpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              upload(view);

            }
        });



//      Membuat Firebase dan instansiasi
        firebaseDatabase= FirebaseDatabase.getInstance("https://pam-project-akhir-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference = firebaseDatabase.getReference();

//      Membuat Loading
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Ditunggu ya gais ... ");


        return this.layout;
    }



    private void tambahData(View view, String avatar) {
        System.out.println("Ini adalah pesan log.");
        String title = etAddTitle.getText().toString();
        String penulis = etAddPenulis.getText().toString();
        String tanggalUpload = etAddTanggalUpload.getText().toString();
        String deskripsi = etAddDeskripsi.getText().toString();
        String avatars = avatar;
        FuncFact baru = new FuncFact(title, deskripsi, penulis, tanggalUpload, avatars);
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


    public  void updateData (View view, String avatar) {
        //                Mengambil data/String dari EditText yang telah diisi oleh Constructor sebelumnya
        String title = etAddTitle.getText().toString();
        String penulis = etAddPenulis.getText().toString();
        String tanggalUpload = etAddTanggalUpload.getText().toString();
        String deskripsi = etAddDeskripsi.getText().toString();
        String avatars = avatar;
        System.out.println("Masuk nih dan Kodenya adalah " + key);
        FuncFact baru = new FuncFact(title, deskripsi,  penulis , tanggalUpload, avatars);
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



//    Menampilkan Dialog "Take Photo" Mengambil foto secara langsung, ""Choose from Library" mengambil dari galeri
    public void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(TambahOrEditFragment.this.getContext());
        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setItems(items, (dialog, item) -> {
            if(items[item].equals("Take Photo")) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            } else if (items[item].equals("Choose from Library")) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 20);
            } else if (items[item].equals("cancel")) {
//                dialog.dismiss();
            }
        });
        builder.show();
    }

//    Menerima data dari galery
//    Menerima data dari gallery, yang pertama di terima adalah URI dan kita ambil pathnya, lalu di decode menjadi bitmap dan ditampilkan ke dalam avatar

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode == Activity.RESULT_OK && data != null){
            final Uri path = data.getData();
            Thread thread = new Thread(() -> {
                try {
                    InputStream inputStream = requireActivity().getContentResolver().openInputStream(path);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    iv_Avatar.post(()-> {
                        iv_Avatar.setImageBitmap(bitmap);
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
            thread.start();
        }
    }


    public void upload(View view) {
        progressDialog.show();
        iv_Avatar.setDrawingCacheEnabled(true);
        iv_Avatar.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) iv_Avatar.getDrawable()).getBitmap();
//        Melakukan Compress Image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
//        Codingan Upload
        FirebaseStorage storage = FirebaseStorage.getInstance();
//        Menyimpan di folder "images"
        StorageReference storageReference = storage.getReference("images").child( "IMG" + new Date().getTime()+ ".jpeg");
        UploadTask uploadTask = storageReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (taskSnapshot.getMetadata() != null) {
                    if(taskSnapshot.getMetadata().getReference() != null) {
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.getResult() != null ){
//                                    23:00'
//                                    saveData (email, name, task.getResult().toString());
                                    if ( isEdit == false){
                                        tambahData(view, task.getResult().toString());
                                    } else if ( isEdit == true ) {
                                        updateData(view, task.getResult().toString());
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });


    }


}