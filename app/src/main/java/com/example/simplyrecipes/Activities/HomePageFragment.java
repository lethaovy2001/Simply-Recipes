package com.example.simplyrecipes.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.simplyrecipes.R;

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

/**
 * Fragment for the Home Page
 * Handles loading different recipes from the EDAMAM API
 *
 * @author William Lai, Dung Vo
 * @version 2.0
 */

public class HomePageFragment extends Fragment{
    private static final String Tag = "HomePageFragment";
    private ImageView recipe_of_the_week_image;
    private TextView popular_recipes_text;
    private String SPOONACULAR_API_KEY = "d166d242afmsh34a43231b52cb39p144850jsn8fe031c85cf5";
    private static final String MEDITTERANIAN_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/random?number=15&limitLicense=false&tags=mediterranean";
    private static final String ASIAN_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/searchComplex?limitLicense=true&offset=0&number=10&cuisine=chinese%2Cjapanese%2Ckorean%2Cvietnamese%2Cthai%2Cindian&ranking=2&instructionsRequired=true";
    private static final String WESTERN_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/searchComplex?limitLicense=true&offset=0&number=10&cuisine=american%2Csouthern%2Cfrench%2Cbritish%2Citalian&ranking=2&instructionsRequired=true";
    private static final String POPULAR_URL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/random?number=15&limitLicense=false";

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




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_home, container, false);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipe_of_the_week_image = view.findViewById(R.id.recipe_of_the_week_image);

        popularRecyclerView = view.findViewById(R.id.popular_recipes_recyclerview);
        popularRecipes = new ArrayList<>();

        asianRecyclerView = view.findViewById(R.id.asian_recipes_recyclerview);
        asianRecipes = new ArrayList<>();

        westernRecyclerView = view.findViewById(R.id.western_recipes_recyclerview);
        westernRecipes = new ArrayList<>();

        mediterraneanRecyclerView = view.findViewById(R.id.mediterranean_recipes_recyclerview);
        mediterraneanRecipes = new ArrayList<>();

//        extractRecipes(MEDITTERANIAN_URL,mediterraneanRecipes, "Meditteranean");
//        extractRecipes(ASIAN_URL, asianRecipes, "Asian");
//        extractRecipes(WESTERN_URL, westernRecipes, "Western");
        extractRecipes(POPULAR_URL, popularRecipes, "Popular");


    }




    /**
     *
     * @param urlString // api url string to search recipes
     * @param recipeList // list to store the recipes retrieve the results
     * @param recipeType // indicating what type of the recipe to set up the right recycler view
     */
    public void extractRecipes(String urlString, final List<Recipe> recipeList, final String recipeType) {
        // use client library to set up the GET request
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlString).get()
                .addHeader("x-rapidapi-key", SPOONACULAR_API_KEY)
                .addHeader("x-rapidapi-host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                .build();

        // call the api's url
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                // response is successful and a JSON object is returned
                Random rand = new Random(); // placeholder for rating values

                if(response.isSuccessful()) {
                    String responseJSON = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(responseJSON);
                        JSONArray jsonArray = null;
                        if(recipeType.equals("Meditteranean") || recipeType.equals("Popular")) {
                            jsonArray = jsonObject.getJSONArray("recipes");
                        }else {
                            jsonArray = jsonObject.getJSONArray("results");
                        }
                        // get the list of the recipe results from the GET request

                        // traverse through the recipes and store them in a recipe list
                        for(int i = 0; i < jsonArray.length(); i++) {

                            JSONObject recipeJSON = jsonArray.getJSONObject(i);

                            int recipeId = recipeJSON.getInt("id");
                            // spoonacular's rating score is not too resourceful, placeholder for now
                            double rating = getRatings(recipeId) + (rand.nextInt(100-80) + 80);
                            String title = recipeJSON.getString("title");
                            String image = recipeJSON.getString("image");


                            Recipe recipe = new Recipe(recipeId, title, image, rating);
                            recipeList.add(recipe);

                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                            switch(recipeType) {
                                case "Asian":
                                    asianRecyclerView.setLayoutManager(layoutManager);
                                    asianAdapter = new HomePageAdapter(getActivity().getApplicationContext(), recipeList);
                                    asianRecyclerView.setAdapter(asianAdapter);
                                    break;
                                case "Western":
                                    westernRecyclerView.setLayoutManager(layoutManager);
                                    westernAdapter = new HomePageAdapter(getActivity().getApplicationContext(), recipeList);
                                    westernRecyclerView.setAdapter(westernAdapter);
                                    break;
                                case "Meditteranean":
                                    mediterraneanRecyclerView.setLayoutManager(layoutManager);
                                    mediterraneanAdapter = new HomePageAdapter(getActivity().getApplicationContext(), recipeList);
                                    mediterraneanRecyclerView.setAdapter(mediterraneanAdapter);
                                    break;
                                case "Popular":
                                    popularRecyclerView.setLayoutManager(layoutManager);
                                    popularAdapter = new HomePageAdapter(getActivity().getApplicationContext(), recipeList);
                                    popularRecyclerView.setAdapter(popularAdapter);
                            }
                        }
                    });
                }
            }
        });
    }


    public String getFragmentTag() {
        return this.Tag;
    }

    public double getRatings(int recipeID) {
        final double[] rating_value = new double[1];
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

                        rating_value[0] = recipeJSON.getDouble("spoonacularScore");

                    }catch (Exception e) {
                        e.printStackTrace();
                        rating_value[0] = -1;
                    }
                }
            }
        });

        return rating_value[0];
    }

}
