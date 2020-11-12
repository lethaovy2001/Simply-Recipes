package com.example.simplyrecipes.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.simplyrecipes.R;

public class ShoppingListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_shopping_list_layout);

        recyclerView = findViewById(R.id.shopping_list_recyclerview);
    }

}