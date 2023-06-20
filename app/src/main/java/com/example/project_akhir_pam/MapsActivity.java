package com.example.project_akhir_pam;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.project_akhir_pam.databinding.FragmentMapBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FragmentMapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = FragmentMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
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