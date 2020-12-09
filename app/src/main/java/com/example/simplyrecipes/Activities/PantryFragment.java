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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplyrecipes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PantryFragment extends Fragment {
    private static final String TAG = "Pantry Fragment";

    FirebaseAuth mAuth;
    DatabaseReference mDbReference; // Pantry
    Button seeRecipeBtn;
    Button add_ingredient_button;
    TextView ingredient_textbox;
    List<Ingredient> mPantryIngredients;
    List<Recipe> mRecipeResults;
    PantryPageAdapter mIngredientAdapter;
    FavoriteAdapter mRecipeResultAdapter;
    RecyclerView mIngredientsView;
    FirebaseDatabase mDatabase;
    boolean existInDatabase;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pantry, container, false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        ingredient_textbox = view.findViewById(R.id.ingredient_textbox);
        mAuth = FirebaseAuth.getInstance();
        mPantryIngredients = new ArrayList<>();
        seeRecipeBtn = view.findViewById(R.id.see_recipe_button);
        mRecipeResults = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance();
        mIngredientsView = view.findViewById(R.id.pantry_recyclerview);
        getPantryList();

        seeRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecipes();
            }
        });
        add_ingredient_button = view.findViewById(R.id.add_ingredient_button);
        add_ingredient_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Method result:" + checkIngredientInputted(ingredient_textbox.getText().toString()));
                if(checkIngredientInputted(ingredient_textbox.getText().toString()) == false) {

                    mPantryIngredients.clear();

                    Random rand = new Random();
                    int num = rand.nextInt(1000);
                    if(!ingredient_textbox.getText().toString().equals("")) {
                        String input = ingredient_textbox.getText().toString();
                        ingredient_textbox.setText("");
                        mDbReference = FirebaseDatabase.getInstance().getReference("users/"+mAuth.getCurrentUser().getUid()+"/Pantry");

                        mDbReference = FirebaseDatabase.getInstance().getReference("users/"+mAuth.getCurrentUser().getUid()+"/Pantry/");
                        mDbReference.child(num+"").child("Ingredient Name").setValue(input).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }

                }else {
                    Toast.makeText(getActivity().getApplicationContext(), "This ingredient is already in the pantry!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void getPantryList() {
        mPantryIngredients.clear();
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



    private void getRecipes() {

        String pantryIngredients = "ingredients=";
        for(int i = 0; i < mPantryIngredients.size(); i++) {
            if(i == 0) {
                pantryIngredients = pantryIngredients + mPantryIngredients.get(i).getIngredientName();
            }
            else {
                pantryIngredients = pantryIngredients+"%2C"+mPantryIngredients.get(i).getIngredientName();
            }
        }

        mIngredientAdapter.clear();

        Toast.makeText(getActivity().getApplicationContext(), pantryIngredients, Toast.LENGTH_SHORT).show();
        OkHttpClient client = new OkHttpClient();
        String urlString = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/findByIngredients?" +
                pantryIngredients+"&number=5&ranking=1&ignorePantry=true";
        Request request = new Request.Builder().url(urlString).get()
                .addHeader("x-rapidapi-key", "d166d242afmsh34a43231b52cb39p144850jsn8fe031c85cf5")
                .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com").build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(getActivity().getApplicationContext(), "Error occurred retrieving the recipes", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                // get recipe results and add them to recipe list
                if(response.isSuccessful()) {
                    String responseJSON = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(responseJSON);

                        for(int i =0; i < jsonArray.length(); i++) {
                            JSONObject currRecipe = jsonArray.getJSONObject(i);

                            int recipeID = currRecipe.getInt("id");
                            String title = currRecipe.getString("title");
                            String image = currRecipe.getString("image");
                            Recipe recipe = new Recipe(recipeID, title, image,-1,-1);

                            mRecipeResults.add(recipe);
                        }
                    }
                    catch(Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), "An Error occurred while retrieving recipes", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                        return;
                    }

                    System.out.println(mRecipeResults.get(0).toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRecipeResultAdapter = new FavoriteAdapter(getActivity().getApplicationContext(),mRecipeResults);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

                            mIngredientsView.setLayoutManager(layoutManager);
                            mIngredientsView.setAdapter(mRecipeResultAdapter);
                        }
                    });
                }
            }
        });
    }

    private boolean checkIngredientInputted(final String ingredient) {
       for(int i = 0; i < mPantryIngredients.size(); i++) {
           if(mPantryIngredients.get(i).getIngredientName().toLowerCase().trim().equals(ingredient.toLowerCase().trim())) {
               return true;
           }
       }
        return false;
    }

}
