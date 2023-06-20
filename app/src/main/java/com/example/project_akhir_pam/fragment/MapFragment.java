package com.example.project_akhir_pam.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_akhir_pam.R;
import com.example.project_akhir_pam.databinding.FragmentMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.SupportMapFragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */



public class MapFragment extends Fragment  implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
//    =========================== TAMBAH ==============================


    private GoogleMap mMap;


//    ============================= END TAMBAH =====================

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        binding = FragmentMapBinding.inflate(inflater, container, false);
//        return binding.getRoot();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.map, mapFragment)
                    .commit();
        }
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        //Persada -7.934282271471119, 112.64997785224708
        // brawijaya -7.940879178507249, 112.62161544808914

        //Rumah Sakit Persada (Persada Hospital)
        LatLng rsPS = new LatLng(-7.934282271471119, 112.64997785224708);
        mMap.addMarker(new MarkerOptions().position(rsPS).title("Persada Hospital"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rsPS));

        //Lavalete
        LatLng rsLav = new LatLng(-7.965499692381833, 112.63787778465496);
        mMap.addMarker(new MarkerOptions().position(rsLav).title("Rumah Sakit Lavalette"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rsLav));

        //Rumah Sakit Universitas Brawijaya
        LatLng rsUB = new LatLng(-7.940879178507249, 112.62161544808914);
        mMap.addMarker(new MarkerOptions().position(rsUB).title("RS Universitas Brawijaya"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rsUB));
    }
}