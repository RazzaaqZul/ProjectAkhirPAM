package com.example.project_akhir_pam.fragment;

import static java.lang.Thread.sleep;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project_akhir_pam.R;
import com.example.project_akhir_pam.RecycleView.MyAdapter;
import com.example.project_akhir_pam.RecycleViewRS.MyAdapterRS;
import com.example.project_akhir_pam.api.ApiConfig;
import com.example.project_akhir_pam.model.FuncFact;
import com.example.project_akhir_pam.model.InformasiUser;
import com.example.project_akhir_pam.model.RumahSakit;
import com.example.project_akhir_pam.modelAPI.BeritaKesahatan;
import com.example.project_akhir_pam.modelAPI.BeritaKesehatanResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


//    BUATAN SENDIRI ===============================


    RecyclerView tv_tampil, tv_tampilRS;
    ArrayList<FuncFact> funcFacts;
    List<BeritaKesahatan> listBeritaKesahatan;
    ArrayList<RumahSakit> rumahSakits;
    private FirebaseAuth mAuth;

    private InformasiUser informasiUser;
    ArrayList<InformasiUser> list;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private  View layout;
    MyAdapter myAdapter;

//    END BUATAN SENDIRI ============================
    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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




//        Logic Buatan Sendiri
        rumahSakits = new ArrayList<RumahSakit>();
        rumahSakits.add(new RumahSakit("Rumah Sakit Persada", "KOMPLEKS ARAYA BUSSINESS CENTER, Jl. Raden Panji Suroso KAV.II-IV", "081130588585", "24 jam" , R.drawable.logo_rs_2));
        rumahSakits.add(new RumahSakit("Rumah Sakit Brawijaya", "Jl. Soekarno - Hatta, Lowokwaru", "0878132879", "24 jam" , R.drawable.logo_rs_3));
        rumahSakits.add(new RumahSakit("Rumah Sakit Lavalette", "Jl. W.R. Supratman No.10", "0861528991", "24 jam" , R.drawable.logo_rs_1));

    }

    TextView tvNama;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.layout =  inflater.inflate(R.layout.fragment_home, container, false);

        tvNama = this.layout.findViewById(R.id.tvNama);
        tv_tampil = this.layout.findViewById(R.id.recyclerViewHome);
//       RUMAH SAKIT HANDLE
        tv_tampilRS = this.layout.findViewById(R.id.recyclerviewRumahSakit);
        MyAdapterRS adapterRS = new MyAdapterRS(HomeFragment.this, rumahSakits);
        tv_tampilRS.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getContext()));
        tv_tampilRS.setAdapter(adapterRS);


        funcFacts = new ArrayList<FuncFact>();

//        Jalankan Fungsi
//        tampilData();
        ambilUsername();
        tampilkanDataBeritaKesehatan();

        return this.layout;
    }






    private void ambilUsername() {
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
                    tvNama.setText("Hello " + note.getUsername().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void tampilkanDataBeritaKesehatan() {
        Call<BeritaKesehatanResponse> req = ApiConfig.getApiService().getNews();
        req.enqueue(new Callback<BeritaKesehatanResponse>() {
            @Override
            public void onResponse(Call<BeritaKesehatanResponse> call, Response<BeritaKesehatanResponse> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        listBeritaKesahatan = response.body().getArticles();

                        System.out.println("BERHASILL");
                        System.out.println(listBeritaKesahatan.get(1).getAuthor());
//                        adapterNews = new NewsAdapter(newsList);
//                        binding.newsRv.setAdapter(adapterNews);
                    }
                }
                menampilkanDataBeritaSatuPerSatu();

                System.out.println("THREAD DIJALANKAN");

            }
            @Override
            public void onFailure(Call<BeritaKesehatanResponse> call, Throwable t) {
                System.out.println("GAGALL");
                System.out.println(call);
            }
 });

    }


    public void menampilkanDataBeritaSatuPerSatu () {

        Handler handler = new Handler();

        // Membuat thread untuk menampilkan item satu per satu
        Thread threads = new Thread(new Runnable() {
            int index = 0;
            @Override
            public void run() {
                System.out.println("SIZE NYA KOSONG MAS BRO");
                System.out.println(listBeritaKesahatan.size());
                while (index < listBeritaKesahatan.size()) {
                    System.out.println("THREAD BERHASIL MASUK");
                    ArrayList<FuncFact> filteredFunFact;
                    filteredFunFact = new ArrayList<FuncFact>();
//                            filteredFunFact.clear();
//                            FuncFact funFact = uncFactfs.get(index);
//                            filteredFunFact.add(funFact);

                    filteredFunFact.add(new FuncFact(listBeritaKesahatan.get(index).getTitle(), listBeritaKesahatan.get(index).getPublishedAt().substring(0,10),listBeritaKesahatan.get(index).getUrl(), "empty", "empty"));

                    try {
                        handler.post(() -> {
                            // Kode disini, berjalan di UI thread
                            // <1>
                            MyAdapter adapter = new MyAdapter(HomeFragment.this, filteredFunFact);
                            tv_tampil.setLayoutManager(new LinearLayoutManager(HomeFragment.this.getContext()));
                            adapter.setFilteredFuncFacts(filteredFunFact);
                            tv_tampil.setAdapter(adapter);

                        });
                        // Sleep akan mengatur setiap detik iv berubah
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println("error : " );
                    }

                    index++;
                    if (index >= listBeritaKesahatan.size()) {
                        index = 0; // Mengulangi dari awal jika sudah mencapai akhir ArrayList
                    }
                }
            }
        });
        threads.start();
    }



}








