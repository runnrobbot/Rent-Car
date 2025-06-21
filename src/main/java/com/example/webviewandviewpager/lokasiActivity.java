package com.example.webviewandviewpager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class lokasiActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap gMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapView2);
        mapFragment.getMapAsync(this);

        ImageButton imageBtnBack = findViewById(R.id.imageBtnBack);
        imageBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(lokasiActivity.this, homePageActivity.class);
                startActivity(i);
            }
        });

        Button btnKembali = (Button)findViewById(R.id.btnSelesaiLokasi);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(lokasiActivity.this, homePageActivity.class);
                startActivity(i);
            }
        });
    }


    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        // Tambahkan marker atau operasikan peta sesuai kebutuhan
        LatLng location = new LatLng(-6.2088, 106.8456);  // Ganti koordinat dengan lokasi yang diinginkan
        gMap.addMarker(new MarkerOptions().position(location).title("ENF MOBILINDO LOCATION"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}