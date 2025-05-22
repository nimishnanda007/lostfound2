package com.example.lostfound2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = findViewById(R.id.btn_add_item);
        btnAdd.setOnClickListener(v ->
            startActivity(new Intent(this, AddItemActivity.class))
        );

        Button btnView = findViewById(R.id.btn_view_items);
        btnView.setOnClickListener(v ->
            startActivity(new Intent(this, ItemListActivity.class))
        );

        Button btnMap = findViewById(R.id.btn_show_on_map);
        btnMap.setOnClickListener(v ->
            startActivity(new Intent(this, MapActivity.class))
        );
    }
}
