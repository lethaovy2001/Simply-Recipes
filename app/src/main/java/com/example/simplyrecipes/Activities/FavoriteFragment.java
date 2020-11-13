package com.example.simplyrecipes.Activities;

import android.os.Bundle;
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

public class FavoriteFragment extends Fragment {

    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseAuth auth;
    List<Recipe> favoriteRecipes;
    RecyclerView favorite_recipe_recyclerview;
    FavoriteAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        favorite_recipe_recyclerview = view.findViewById(R.id.favorite_recipe_recyclerview);
        favoriteRecipes = new ArrayList<>();
        getFavoriteRecipes();


    }

    private void getFavoriteRecipes() {
        reference = FirebaseDatabase.getInstance().getReference("users/"+auth.getCurrentUser().getUid()+"/Favorite");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap : snapshot.getChildren()) {
                    System.out.println(snap.getKey().toString());
                    if(!snap.getKey().toString().equals("none")) {
                        int recipeID = Integer.parseInt(snap.getKey().toString());
                        String recipeName = null;
                        String recipeURL = null;
                        String recipeTime = null;
                        for(DataSnapshot ds: snap.getChildren()) {
                            if(ds.getKey().toString().equals("Recipe Name")) {
                                recipeName = ds.getValue().toString();
                                System.out.println(recipeName.toString());
                            }
                            else if (ds.getKey().toString().equals("Recipe Time")) {
                                recipeTime = ds.getValue().toString();
                                System.out.println(recipeTime.toString());
                            }
                            else if(ds.getKey().toString().equals("Recipe URL")) {
                                recipeURL = ds.getValue().toString();
                                System.out.println(recipeURL.toString());
                            }
                        }
                        if(recipeName == null) {
                            recipeName = "";
                        }
                        if(recipeTime == null) {
                            recipeTime = "-1";
                        }
                        if(recipeURL == null) {
                            recipeURL = "";
                        }
                        System.out.println(recipeID+", "+recipeName+", "+ recipeURL+", "+ recipeTime);
                        Recipe currRecipe = new Recipe(recipeID, recipeName, recipeURL, Integer.parseInt(recipeTime));
                        favoriteRecipes.add(currRecipe);
                    }
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new FavoriteAdapter(getActivity().getApplicationContext(), favoriteRecipes);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

                        favorite_recipe_recyclerview.setLayoutManager(layoutManager);
                        favorite_recipe_recyclerview.setAdapter(adapter);
                    }
                });


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error" + error.getMessage());
            }

        });
    }
}
