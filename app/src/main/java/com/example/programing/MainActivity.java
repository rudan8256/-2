package com.example.programing;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        LatLng KNU = new LatLng(35.88939232163954, 128.61029970124557);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(KNU);
        markerOptions.title("경북대학교");
        markerOptions.snippet("대구캠퍼스");
        mMap.addMarker(markerOptions);
        markerOptions.snippet("대구시 장애인전용 체육시설");
        mMap.addMarker(markerOptions.position(new LatLng(35.82753459725342, 128.6068409605555))
                .title("대구장애인종합복지관"));
        mMap.addMarker(markerOptions.position(new LatLng(35.85314870732212, 128.5253715285834))
                .title("대구시달구벌종합스포츠센타"));
        mMap.addMarker(markerOptions.position(new LatLng(35.82536966215067, 128.68472745556284))
                .title("대구시달구벌종합스포츠센타"));
        //추가예정
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(KNU, 11));
    }
}