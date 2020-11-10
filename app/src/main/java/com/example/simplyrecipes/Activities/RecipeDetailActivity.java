package com.example.simplyrecipes.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simplyrecipes.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecipeDetailActivity extends AppCompatActivity {
    // GLOBALS//
    private static final String Tag = "RecipeDetailFragment";
    int recipeID;

    ImageView recipe_image;
    TextView recipe_name;
    TextView recipe_detail;
    TextView cooking_time_val;
    TextView calories_val;
    TextView rating_val;
    TextView instruction_detail_text;


    RecyclerView ingredient_list_recycler_view;
    List<Ingredient> ingredientList;

    IngredientAdapter ingredientAdapter;

    private String SPOONACULAR_API_KEY = "d166d242afmsh34a43231b52cb39p144850jsn8fe031c85cf5";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recipe_detail_page);
        recipeID = getIntent().getIntExtra("recipeID", -100);
        ingredientList = new ArrayList<>();

        recipe_image = findViewById(R.id.recipe_image);
        recipe_name = findViewById(R.id.recipe_name);
        recipe_detail = findViewById(R.id.recipe_detail);
        cooking_time_val = findViewById(R.id.cooking_time_val);
        calories_val = findViewById(R.id.calories_val);
        rating_val = findViewById(R.id.rating_val);
        instruction_detail_text = findViewById(R.id.instruction_detail_text);


        ingredient_list_recycler_view = findViewById(R.id.ingredient_list_recycler_view);

        if(recipeID > 0) {
            RecipeDetailsSetup(recipeID);
        }
        // setting up the recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ingredient_list_recycler_view.setLayoutManager(layoutManager);
        ingredientAdapter = new IngredientAdapter(this, ingredientList);
        ingredient_list_recycler_view.setAdapter(ingredientAdapter);
    }

    private void RecipeDetailsSetup(int recipeID) {
        OkHttpClient client = new OkHttpClient();
        String urlString = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/" + Integer.toString(recipeID)+ "/information?includeNutrition=true";
        Request request = new Request.Builder().url(urlString).get()
                .addHeader("x-rapidapi-key", SPOONACULAR_API_KEY)
                .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    String responseJSON = response.body().string();

                    try {
                        JSONObject recipeJSON = new JSONObject(responseJSON);
                        final String title = recipeJSON.getString("title");
                        final String image = recipeJSON.getString("image");
                        final String instructions = recipeJSON.getString("instructions");
                        final int cooking_time = recipeJSON.getInt("readyInMinutes");
                        // getting calories
                        JSONObject nutritionJSON = new JSONObject(recipeJSON.getString("nutrition"));
                        JSONArray nutrientsJSON = nutritionJSON.getJSONArray("nutrients");
                        JSONObject calorieJSON = nutrientsJSON.getJSONObject(0); // get's the calorie JSON
                        final double calories = calorieJSON.getDouble("amount"); // get calories amount from calorieJSON
                        final double rating = recipeJSON.getDouble("spoonacularScore");
                        final Document detail = Jsoup.parse(recipeJSON.getString("summary"));

                        JSONArray recipeIngredients = recipeJSON.getJSONArray("extendedIngredients");
                        for(int i = 0; i < recipeIngredients.length(); i++) {
                            JSONObject currIngredient = new JSONObject(recipeIngredients.get(i).toString());
                            int ingredientID = currIngredient.getInt("id");
                            String ingredientName = currIngredient.getString("name");
                            Number ingredientAmountNum = currIngredient.getDouble("amount");
                            String units = currIngredient.getString("unit");
                            String aisle = currIngredient.getString("aisle");
                            String ingredientAmount = ingredientAmountNum + " " +  units;
                            Ingredient ingredient = new Ingredient(ingredientID, ingredientAmount, ingredientName,aisle);
                            ingredientList.add(ingredient);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Picasso.get().load(image).fit().into(recipe_image);
                                recipe_name.setText(title);
                                instruction_detail_text.setText(instructions);
                                cooking_time_val.setText(Integer.toString(cooking_time) + " min");
                                calories_val.setText(""+calories);
                                rating_val.setText(Double.toString(rating));
                                recipe_detail.setText(detail.text());

                            }
                        });
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
