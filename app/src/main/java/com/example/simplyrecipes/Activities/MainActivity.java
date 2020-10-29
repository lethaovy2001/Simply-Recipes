package com.example.simplyrecipes.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.simplyrecipes.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.HttpUrl;


public class MainActivity extends AppCompatActivity {

    private ImageView recipe_of_the_week_image;
    private TextView popular_recipes_text;

    String recipeOfWeekImageUrl = null;
    RecyclerView popularRecyclerView;
    RecyclerView asianRecyclerView;
    RecyclerView westernRecyclerView;
    RecyclerView mediterraneanRecyclerView;
    List<Recipe> popularRecipes;
    List<Recipe> asianRecipes;
    List<Recipe> westernRecipes;
    List<Recipe> mediterraneanRecipes;
    HomePageAdapter popularAdapter;
    HomePageAdapter asianAdapter;
    HomePageAdapter westernAdapter;
    HomePageAdapter mediterraneanAdapter;

    private static final String EDAMAM_BASE_URL = "https://api.edamam.com/search";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_home);
        recipe_of_the_week_image = findViewById(R.id.recipe_of_the_week_image);

        popularRecyclerView = findViewById(R.id.popular_recipes_recyclerview);
        popularRecipes = new ArrayList<>();

        asianRecyclerView = findViewById(R.id.asian_recipes_recyclerview);
        asianRecipes = new ArrayList<>();

        westernRecyclerView = findViewById(R.id.western_recipes_recyclerview);
        westernRecipes = new ArrayList<>();

        mediterraneanRecyclerView = findViewById(R.id.mediterranean_recipes_recyclerview);
        mediterraneanRecipes = new ArrayList<>();

        extractRecipeOfWeek();
        extractPopularRecipes();
        extractAsianRecipes();
        extractWesternRecipes();
        extractMediterraneanRecipes();
    }

    private void extractMediterraneanRecipes() {
        // build the url for EDAMAM Search API

        HttpUrl.Builder urlBuilder = HttpUrl.parse(EDAMAM_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("q", "mediterranean");
        // displays 30 results from the query
        urlBuilder.addQueryParameter("from", "4");
        urlBuilder.addQueryParameter("to","10");
        // application key and id from Edamam
        urlBuilder.addQueryParameter("app_id", "4dc026e9");
        urlBuilder.addQueryParameter("app_key", "7c6bbd8526153dcbcb3e8d96b46696c6");

        String url = urlBuilder.build().toString();
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray recipesJSON = response.getJSONArray("hits");
                            Log.d("Debug", "recipesLength = "+ response.toString());
                            for(int i = 0; i < recipesJSON.length();i++) {
                                // receiving a specific hit
                                JSONObject recipeJson = recipesJSON.getJSONObject(i);
                                // recipe portion of the hit
                                JSONObject currentRecipe = recipeJson.getJSONObject("recipe");
                                String title = currentRecipe.getString("label");
                                String image = currentRecipe.getString("image");
                                String url = currentRecipe.getString("url");
                                String calories = Integer.toString(currentRecipe.getInt("calories"));
                                String servings = Integer.toString(currentRecipe.getInt("yield"));

                                // getting ingredients of the recipe
                                ArrayList<String> ingredientList = new ArrayList<>();
                                JSONArray recipeIngredients = currentRecipe.getJSONArray("ingredientLines");
                                for(int j =0; j < recipeIngredients.length(); j++) {
                                    ingredientList.add(recipeIngredients.get(j).toString());
                                }
                                Recipe recipe = new Recipe(title,image,url, ingredientList,calories,servings);
                                mediterraneanRecipes.add(recipe);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
                        mediterraneanRecyclerView.setLayoutManager(layoutManager);
                        mediterraneanAdapter = new HomePageAdapter(getApplicationContext(), mediterraneanRecipes);
                        mediterraneanRecyclerView.setAdapter(mediterraneanAdapter);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.getMessage());
                    }
                });
        queue.add(jsonObjectRequest);
    }

    private void extractWesternRecipes() {
        // build the url for EDAMAM Search API

        HttpUrl.Builder urlBuilder = HttpUrl.parse(EDAMAM_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("q", "American");
        // displays 30 results from the query
        urlBuilder.addQueryParameter("from", "4");
        urlBuilder.addQueryParameter("to","34");
        // application key and id from Edamam
        urlBuilder.addQueryParameter("app_id", "4dc026e9");
        urlBuilder.addQueryParameter("app_key", "7c6bbd8526153dcbcb3e8d96b46696c6");

        String url = urlBuilder.build().toString();
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray recipesJSON = response.getJSONArray("hits");
                            Log.d("Debug", "recipesLength = "+ response.toString());
                            for(int i = 0; i < recipesJSON.length();i++) {
                                // receiving a specific hit
                                JSONObject recipeJson = recipesJSON.getJSONObject(i);
                                // recipe portion of the hit
                                JSONObject currentRecipe = recipeJson.getJSONObject("recipe");
                                String title = currentRecipe.getString("label");
                                String image = currentRecipe.getString("image");
                                String url = currentRecipe.getString("url");
                                String calories = Integer.toString(currentRecipe.getInt("calories"));
                                String servings = Integer.toString(currentRecipe.getInt("yield"));

                                // getting ingredients of the recipe
                                ArrayList<String> ingredientList = new ArrayList<>();
                                JSONArray recipeIngredients = currentRecipe.getJSONArray("ingredientLines");
                                for(int j =0; j < recipeIngredients.length(); j++) {
                                    ingredientList.add(recipeIngredients.get(j).toString());
                                }
                                Recipe recipe = new Recipe(title,image,url, ingredientList,calories,servings);
                                westernRecipes.add(recipe);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
                        westernRecyclerView.setLayoutManager(layoutManager);
                        westernAdapter = new HomePageAdapter(getApplicationContext(), westernRecipes);
                        westernRecyclerView.setAdapter(westernAdapter);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.getMessage());
                    }
                });

        queue.add(jsonObjectRequest);
    }

    private void extractRecipeOfWeek() {
        Random rand = new Random();
        final int recipeIndex = rand.nextInt(100);

        HttpUrl.Builder urlBuilder = HttpUrl.parse(EDAMAM_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("q", "steak");
        // displays 30 results from the query
        urlBuilder.addQueryParameter("from", "0");
        urlBuilder.addQueryParameter("to","30");
        // application key and id from Edamam
        urlBuilder.addQueryParameter("app_id", "4dc026e9");
        urlBuilder.addQueryParameter("app_key", "7c6bbd8526153dcbcb3e8d96b46696c6");
        String url = urlBuilder.build().toString();
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest

                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray recipesJSON = response.getJSONArray("hits");
                            // take a random index from the hits
                            JSONObject weekRecipe = recipesJSON.getJSONObject(recipeIndex);
                            JSONObject recipe = weekRecipe.getJSONObject("recipe");
                            String image = recipe.getString("image");
                            Picasso.get().load(image).fit().into(recipe_of_the_week_image);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", "image error: " + error.getMessage());
                    }
                });
        queue.add(jsonObjectRequest);

    }

    private void extractAsianRecipes() {
        // build the url for EDAMAM Search API

        HttpUrl.Builder urlBuilder = HttpUrl.parse(EDAMAM_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("q", "asian, chinese");
        // displays 30 results from the query
        urlBuilder.addQueryParameter("from", "0");
        urlBuilder.addQueryParameter("to","30");
        // application key and id from Edamam
        urlBuilder.addQueryParameter("app_id", "4dc026e9");
        urlBuilder.addQueryParameter("app_key", "7c6bbd8526153dcbcb3e8d96b46696c6");

        String url = urlBuilder.build().toString();
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray recipesJSON = response.getJSONArray("hits");
                            Log.d("Debug", "recipesLength = "+ response.toString());
                            for(int i = 0; i < recipesJSON.length();i++) {
                                // receiving a specific hit
                                JSONObject recipeJson = recipesJSON.getJSONObject(i);
                                // recipe portion of the hit
                                JSONObject currentRecipe = recipeJson.getJSONObject("recipe");
                                String title = currentRecipe.getString("label");
                                String image = currentRecipe.getString("image");
                                String url = currentRecipe.getString("url");
                                String calories = Integer.toString(currentRecipe.getInt("calories"));
                                String servings = Integer.toString(currentRecipe.getInt("yield"));

                                // getting ingredients of the recipe
                                ArrayList<String> ingredientList = new ArrayList<>();
                                JSONArray recipeIngredients = currentRecipe.getJSONArray("ingredientLines");
                                for(int j =0; j < recipeIngredients.length(); j++) {
                                    ingredientList.add(recipeIngredients.get(j).toString());
                                }
                                Recipe recipe = new Recipe(title,image,url, ingredientList,calories,servings);
                                asianRecipes.add(recipe);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
                        asianRecyclerView.setLayoutManager(layoutManager);
                        asianAdapter = new HomePageAdapter(getApplicationContext(), asianRecipes);
                        asianRecyclerView.setAdapter(asianAdapter);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.getMessage());
                    }
                });
        queue.add(jsonObjectRequest);
    }

    private void extractPopularRecipes() {

        // build the url for EDAMAM Search API

        HttpUrl.Builder urlBuilder = HttpUrl.parse(EDAMAM_BASE_URL).newBuilder();
        // just using chicken for now, will having to figure out what constitutes as popular
        urlBuilder.addQueryParameter("q", "chicken");
        // displays 30 results from the query
        urlBuilder.addQueryParameter("from", "0");
        urlBuilder.addQueryParameter("to","30");
        // application key and id from Edamam
        urlBuilder.addQueryParameter("app_id", "4dc026e9");
        urlBuilder.addQueryParameter("app_key", "7c6bbd8526153dcbcb3e8d96b46696c6");

        String url = urlBuilder.build().toString();
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray recipesJSON = response.getJSONArray("hits");

                            for(int i = 0; i < recipesJSON.length();i++) {
                                // receiving a specific hit
                                JSONObject recipeJson = recipesJSON.getJSONObject(i);
                                // recipe portion of the hit
                                JSONObject currentRecipe = recipeJson.getJSONObject("recipe");
                                String title = currentRecipe.getString("label");
                                String image = currentRecipe.getString("image");
                                String url = currentRecipe.getString("url");
                                String calories = Integer.toString(currentRecipe.getInt("calories"));
                                String servings = Integer.toString(currentRecipe.getInt("yield"));

                                // getting ingredients of the recipe
                                ArrayList<String> ingredientList = new ArrayList<>();
                                JSONArray recipeIngredients = currentRecipe.getJSONArray("ingredientLines");
                                for(int j =0; j < recipeIngredients.length(); j++) {
                                    ingredientList.add(recipeIngredients.get(j).toString());
                                }
                                Recipe recipe = new Recipe(title,image,url, ingredientList,calories,servings);
                                popularRecipes.add(recipe);
                            }
                            Log.d("Debug", "recipesLength = "+ popularRecipes.size());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
                        popularRecyclerView.setLayoutManager(layoutManager);
                        popularAdapter = new HomePageAdapter(getApplicationContext(), popularRecipes);
                        popularRecyclerView.setAdapter(popularAdapter);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", error.getMessage());
                    }
                });

        queue.add(jsonObjectRequest);
    }
}
