package com.example.simplyrecipes.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

public class PantryFragment extends Fragment {
    private static final String TAG = "Pantry Fragment";

    FirebaseAuth mAuth;
    DatabaseReference mDbReference; // Pantry
    List<Ingredient> mPantryIngredients;
    PantryPageAdapter mIngredientAdapter;
    RecyclerView mIngredientsView;
    FirebaseDatabase mDatabase;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pantry, container, false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mPantryIngredients = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mIngredientsView = view.findViewById(R.id.pantry_recyclerview);
        getPantryList();
    }

    private void getPantryList() {
        mDbReference =
                FirebaseDatabase.getInstance().getReference("users/" + mAuth.getCurrentUser().getUid() + "/Pantry");
        mDbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if (!snap.getKey().equals("none")) {
                        System.out.println(snap.getKey());
                         int ingredientId = Integer.parseInt(snap.getKey());
                        String ingredientName = null;
                        String ingredientCategory = null;
                        for (DataSnapshot ds : snap.getChildren()) {
                            System.out.println(ds.getKey().toString());
                            if (ds.getKey().equals("Ingredient Name")) {
                                ingredientName = ds.getValue().toString();
                            } else if (ds.getKey().equals("Ingredient Category")) {
                                ingredientCategory = ds.getValue().toString();
                            }
                        }
                        Ingredient ingredient = new Ingredient(ingredientId, ingredientName,
                                ingredientCategory);
                        mPantryIngredients.add(ingredient);
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mIngredientAdapter =
                                new PantryPageAdapter(getActivity().getApplicationContext(),
                                mPantryIngredients);
                        LinearLayoutManager layoutManager =
                                new LinearLayoutManager(getActivity().getApplicationContext());
                        mIngredientsView.setLayoutManager(layoutManager);
                        mIngredientsView.setAdapter(mIngredientAdapter);
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
