package com.example.lostfound2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemListActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener {
    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        dbHelper = new DBHelper(this);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadItems();
    }

    private void loadItems() {
        List<Item> items = dbHelper.getAllItems();
        if (items != null && !items.isEmpty()) {
            adapter = new ItemAdapter(this, items, this);
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No items found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(Item item) {
        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra("ITEM_ID", item.getId());
        startActivity(intent);
    }
}
