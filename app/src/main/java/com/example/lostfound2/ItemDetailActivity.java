package com.example.lostfound2;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private TextView tvTitleDetail, tvDescDetail, tvLocationDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        dbHelper = new DBHelper(this);

        tvTitleDetail    = findViewById(R.id.tv_title_detail);
        tvDescDetail     = findViewById(R.id.tv_desc_detail);
        tvLocationDetail = findViewById(R.id.tv_location_detail);


        int itemId = getIntent().getIntExtra("ITEM_ID", -1);
        if (itemId == -1) {
            Toast.makeText(this, "Invalid item ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        Cursor cursor = dbHelper.getItemById(itemId);
        if (cursor != null && cursor.moveToFirst()) {
            String name   = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_NAME));
            String desc   = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_DESC));
            String status = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COL_STATUS));
            double lat    = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COL_LAT));
            double lng    = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COL_LNG));
            cursor.close();


            tvTitleDetail.setText(status + ": " + name);
            tvDescDetail.setText(desc);
            tvLocationDetail.setText(
                String.format("Location: %.6f, %.6f", lat, lng)
            );
        } else {
            if (cursor != null) cursor.close();
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
