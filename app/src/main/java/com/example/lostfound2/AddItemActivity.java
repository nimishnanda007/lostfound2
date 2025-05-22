package com.example.lostfound2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {
    private double selectedLat, selectedLng;
    private EditText etName, etPhone, etDesc, etDate;
    private RadioGroup rgStatus;
    private Button btnSave;
    private DBHelper dbHelper;
    private final Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        dbHelper = new DBHelper(this);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        }


        AutocompleteSupportFragment acFrag = (AutocompleteSupportFragment)
            getSupportFragmentManager().findFragmentById(R.id.place_autocomplete);
        acFrag.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        acFrag.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                if (place.getLatLng() != null) {
                    selectedLat = place.getLatLng().latitude;
                    selectedLng = place.getLatLng().longitude;
                    Toast.makeText(AddItemActivity.this,
                        "Location: " + place.getName(),
                        Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(com.google.android.gms.common.api.Status status) {
                Toast.makeText(AddItemActivity.this,
                    "Place error: " + status.getStatusMessage(),
                    Toast.LENGTH_SHORT).show();
            }
        });


        rgStatus = findViewById(R.id.rg_status);
        etName   = findViewById(R.id.et_person_name);
        etPhone  = findViewById(R.id.et_phone);
        etDesc   = findViewById(R.id.et_description);
        etDate   = findViewById(R.id.et_date);
        btnSave  = findViewById(R.id.btn_save);

        etDate.setOnClickListener(v -> {
            new DatePickerDialog(AddItemActivity.this,
                (DatePicker view, int year, int month, int day) -> {
                    // month is 0-based
                    String dateStr = String.format("%04d-%02d-%02d",
                        year, month + 1, day);
                    etDate.setText(dateStr);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });


        btnSave.setOnClickListener(v -> {
            String status = (rgStatus.getCheckedRadioButtonId() == R.id.rb_lost)
                ? "Lost" : "Found";
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();
            String date = etDate.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || desc.isEmpty()
                || date.isEmpty() || selectedLat == 0 && selectedLng == 0) {
                Toast.makeText(this,
                    "Fill all fields & pick a location",
                    Toast.LENGTH_SHORT).show();
                return;
            }

            long id = dbHelper.insertItem(
                name, phone, desc, date, status, selectedLat, selectedLng
            );
            if (id > 0) {
                Toast.makeText(this, "Item saved!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "DB error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
