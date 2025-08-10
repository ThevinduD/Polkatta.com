package com.example.polkattacom.Activity;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;

import com.example.polkattacom.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class StoreMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    // Set your store’s coordinates here (example: Colombo)
    private static final double STORE_LATITUDE = 6.8773;
    private static final double STORE_LONGITUDE = 79.9235;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng storeLocation = new LatLng(STORE_LATITUDE, STORE_LONGITUDE);

        // Add marker for store location
        mMap.addMarker(new MarkerOptions().position(storeLocation).title("Our Store"));
        // Move camera and zoom in
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(storeLocation, 15));
    }
}

