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
        adapter = new FavoriteAdapter(getActivity().getApplicationContext(), ApplicationClass.currentUser.favoriteRecipes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext()
                , LinearLayoutManager.HORIZONTAL, false);
        favorite_recipe_recyclerview.setLayoutManager(layoutManager);
        favorite_recipe_recyclerview.setAdapter(adapter);

        int length = 0;


//        reference = FirebaseDatabase.getInstance().getReference("Favorites/users/"+ auth.getCurrentUser().getUid());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                System.out.println("Starting count");
//                int count = 0;
//                for(DataSnapshot snap : snapshot.getChildren()) {
//                    System.out.println(snap.getKey().toString());
//                }
//                System.out.println(count);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                System.out.println("Error" + error.getMessage());
//            }
//        });


    }

    private void getRecipes(int recipeID) {

    }
}
