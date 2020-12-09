package com.example.simplyrecipes.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplyrecipes.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListFragment extends Fragment {
    FirebaseAuth auth;
    DatabaseReference reference;
    List<Ingredient> shoppingListIngredients;
    ShoppingListAdapter adapter;
    RecyclerView shopping_list_recyclerview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        shoppingListIngredients = new ArrayList<>();
        shopping_list_recyclerview = view.findViewById(R.id.shopping_list_recyclerview);
        getShoppingList();

    }

    private void getShoppingList() {
        reference =
                FirebaseDatabase.getInstance().getReference("users/" + auth.getCurrentUser().getUid() + "/Shopping List");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shoppingListIngredients.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if (!snap.getKey().toString().equals("none")) {

                        int ingredientId = Integer.parseInt(snap.getKey());
                        String ingredientName = null;
                        String ingredientCategory = null;
                        for (DataSnapshot ds : snap.getChildren()) {

                            if (ds.getKey().equals("Ingredient Name")) {
                                ingredientName = ds.getValue().toString();
                            } else if (ds.getKey().equals("Ingredient Category")) {
                                ingredientCategory = ds.getValue().toString();
                            }
                        }
                        Ingredient ingredient = new Ingredient(ingredientId, ingredientName, ingredientCategory);
                        shoppingListIngredients.add(ingredient);
                    }
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ShoppingListAdapter(getActivity().getApplicationContext(),
                                shoppingListIngredients);
                        LinearLayoutManager layoutManager =
                                new LinearLayoutManager(getActivity().getApplicationContext());
                        shopping_list_recyclerview.setLayoutManager(layoutManager);
                        shopping_list_recyclerview.setAdapter(adapter);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("error", error.getMessage());
            }
        });
    }
}
