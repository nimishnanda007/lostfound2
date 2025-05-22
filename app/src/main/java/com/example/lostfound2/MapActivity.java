package com.example.lostfound2;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        dbHelper = new DBHelper(this);

        SupportMapFragment mapFragment = (SupportMapFragment)
            getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<Item> items = dbHelper.getAllItems();
        if (items.isEmpty()) {
            Toast.makeText(this, "No items to show on map", Toast.LENGTH_SHORT).show();
            return;
        }

        for (Item item : items) {
            LatLng pos = new LatLng(item.getLatitude(), item.getLongitude());
            String title = item.getStatus() + ": " + item.getPersonName();
            mMap.addMarker(new MarkerOptions()
                .position(pos)
                .title(title)
            );
        }

        LatLng firstPos = new LatLng(
            items.get(0).getLatitude(),
            items.get(0).getLongitude()
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstPos, 12f));
    }
}
