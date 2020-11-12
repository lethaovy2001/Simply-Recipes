package com.example.simplyrecipes.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.simplyrecipes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

    ToggleButton favorite_toggle; // using it as a save button for now

    RecyclerView ingredient_list_recycler_view;
    List<Ingredient> ingredientList;
    IngredientAdapter ingredientAdapter;
    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseAuth auth;


    private String SPOONACULAR_API_KEY = "d166d242afmsh34a43231b52cb39p144850jsn8fe031c85cf5";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // retrieve the recipe ID from the intent

        setContentView(R.layout.recipe_detail_page);

        recipeID = getIntent().getIntExtra("recipeID", -100);
        ingredientList = new ArrayList<>();

        // initialize the views
        recipe_image = findViewById(R.id.recipe_image);
        recipe_name = findViewById(R.id.recipe_name);
        recipe_detail = findViewById(R.id.recipe_detail);
        cooking_time_val = findViewById(R.id.cooking_time_val);
        calories_val = findViewById(R.id.calories_val);
        rating_val = findViewById(R.id.rating_val);
        instruction_detail_text = findViewById(R.id.instruction_detail_text);
        ingredient_list_recycler_view = findViewById(R.id.ingredient_list_recycler_view);
        favorite_toggle = findViewById(R.id.favorite_toggle);
        favorite_toggle.setClickable(true);

        // just in case something happens to the recipeID from the intent transaction
        if(recipeID > 0) {
            // getting the recipe information
            RecipeDetailsSetup(recipeID);
        }
        // setting up the recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ingredient_list_recycler_view.setLayoutManager(layoutManager);
        ingredientAdapter = new IngredientAdapter(this, ingredientList);
        ingredient_list_recycler_view.setAdapter(ingredientAdapter);

        // save button function

        favorite_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth = FirebaseAuth.getInstance();
                db = FirebaseDatabase.getInstance();
                reference = db.getReference("Favorites/users/"+ auth.getCurrentUser().getUid());

                reference.child(recipe_name.getText().toString()).setValue(recipeID).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(RecipeDetailActivity.this, recipe_name.getText().toString()+" Successully Saved", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RecipeDetailActivity.this, recipe_name.getText().toString()+" Failed to Save", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    /**
     *
     * @param recipeID
     * Does an API call on a particular recipe to recieve the JSON of that particular recipe and set
     * the details page
     */
    private void RecipeDetailsSetup(int recipeID) {
        // set up the client and the respective GET request
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
                    // JSON result
                    String responseJSON = response.body().string();

                    try {
                        // storing the needed recipe information
                        JSONObject recipeJSON = new JSONObject(responseJSON);
                        final String title = recipeJSON.getString("title"); // recipe title
                        final String image = recipeJSON.getString("image"); // recipe image url
                        final String instructions = recipeJSON.getString("instructions"); //recipe's instructions
                        final int cooking_time = recipeJSON.getInt("readyInMinutes"); //cooking time

                        // getting calories (within another JSON array of the JSONobject
                        JSONObject nutritionJSON = new JSONObject(recipeJSON.getString("nutrition"));
                        JSONArray nutrientsJSON = nutritionJSON.getJSONArray("nutrients");
                        JSONObject calorieJSON = nutrientsJSON.getJSONObject(0); // get's the calorie JSON

                        final double calories = calorieJSON.getDouble("amount"); // get calories amount from calorieJSON
                        final double rating = recipeJSON.getDouble("spoonacularScore");
                        final Document detail = Jsoup.parse(recipeJSON.getString("summary"));

                        // get the ingredients JSONArray
                        JSONArray recipeIngredients = recipeJSON.getJSONArray("extendedIngredients");
                        //travese through the ingredients of the JSONArray
                        for(int i = 0; i < recipeIngredients.length(); i++) {
                            // for each ingredient
                            JSONObject currIngredient = new JSONObject(recipeIngredients.get(i).toString());
                            int ingredientID = currIngredient.getInt("id");
                            String ingredientName = currIngredient.getString("name");
                            Number ingredientAmountNum = currIngredient.getDouble("amount");
                            String units = currIngredient.getString("unit"); // units for measurement
                            String aisle = currIngredient.getString("aisle"); // ingredient type
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
                                calories_val.setText(calories+" calories");
                                rating_val.setText(Double.toString(rating) + "%");
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
