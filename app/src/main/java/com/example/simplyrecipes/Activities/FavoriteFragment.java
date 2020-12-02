package com.example.simplyrecipes.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ToggleButton;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class FavoriteFragment extends Fragment {

    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseAuth auth;
    List<Recipe> favoriteRecipes;
    RecyclerView favorite_recipe_recyclerview, filter_recyclerview;
    FavoriteAdapter adapter;
    FilterAdapter filterAdapter;
    ToggleButton mealTypeToggleBtn, cuisineToggleBtn, cookingTimeToggleBtn, ratingToggleBtn;
    TextView selectFilterTextView;
    ImageView exitFilterPopupImageView;
    PopupWindow popupWindow;
    Button applyFilterButton;
    LayoutInflater inflater;
    View popupView;
    Filter filters;
    HashMap<String, Set<String>> selectedFilters;
    Context context;

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
        mealTypeToggleBtn = view.findViewById(R.id.meal_type_toggle_btn);
        cuisineToggleBtn = view.findViewById(R.id.cuisine_btn);
        cookingTimeToggleBtn = view.findViewById(R.id.cooking_time_btn);
        ratingToggleBtn = view.findViewById(R.id.rating_btn);
        favoriteRecipes = new ArrayList<>();
        filters = new Filter();
        selectedFilters = new HashMap<>();
        context = getActivity().getApplicationContext();
        getFavoriteRecipes();
        addListenerOnToggleButtonClick();
    }

    private void applyFilter() {
        List<Recipe> filteredRecipes = new ArrayList<>();
        int index = 0;
        int countedFilters = 0;
        int totalFilters = selectedFilters.size();
        if (totalFilters == 0) {
            return;
        }

        for (Recipe recipe: favoriteRecipes) {
            countedFilters = 0;
            if (selectedFilters.containsKey("Rating")) {
                double recipeRating = recipe.getRecipeRating() * 5 / 100;
                if (selectedFilters.get("Rating").contains("4.0 - 5.0") && recipeRating >= 4.0 && recipeRating <= 5.0) {
                    countedFilters += 1;
                } else if (selectedFilters.get("Rating").contains("3.0 - 4.0") && recipeRating >= 3.0 && recipeRating < 4.0) {
                    countedFilters += 1;
                } else if (selectedFilters.get("Rating").contains("2.0 - 3.0") && recipeRating >= 2.0 && recipeRating < 3.0) {
                    countedFilters += 1;
                } else if (selectedFilters.get("Rating").contains("1.0 - 2.0") && recipeRating >= 1.0 && recipeRating < 2.0) {
                    countedFilters += 1;
                }
            }

            if (selectedFilters.containsKey("Cooking Time")) {
                Log.d("**crash", "1");
                Set<String> ratingOptions = selectedFilters.get("Cooking Time");
                if (ratingOptions.contains("Less than 15 minutes") && recipe.getRecipeTime() < 15) {
                    countedFilters += 1;
                } else if (ratingOptions.contains("15 - 30 minutes") && recipe.getRecipeTime() >= 15 && recipe.getRecipeTime() < 30) {
                    countedFilters += 1;
                } else if (ratingOptions.contains("30 - 60 minutes") && recipe.getRecipeTime() >= 30 && recipe.getRecipeTime() < 60) {
                    countedFilters += 1;
                } else if (ratingOptions.contains("60 - 120 minutes") && recipe.getRecipeTime() >= 60 && recipe.getRecipeTime() < 120) {
                    countedFilters += 1;
                } else if (ratingOptions.contains("More than 120 minutes") && recipe.getRecipeTime() >= 120) {
                    countedFilters += 1;
                }
            }

            if (selectedFilters.containsKey(""))

            if (countedFilters == totalFilters) {
                filteredRecipes.add(recipe);
            }

            index += 1;
        }

        for (Recipe recipe: filteredRecipes) {
            Log.d("**recipe", recipe.getTitle() + " ");
        }
        favoriteRecipes.clear();
        favoriteRecipes.addAll(filteredRecipes);
        adapter.notifyDataSetChanged();
    }

    private void getFavoriteRecipes() {
        reference = FirebaseDatabase.getInstance().getReference("users/" + auth.getCurrentUser().getUid() + "/Favorite");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoriteRecipes.clear();
                for (final DataSnapshot snap : snapshot.getChildren()) {

                    if (!snap.getKey().toString().equals("none")) {
                        int recipeID = Integer.parseInt(snap.getKey().toString());
                        String recipeName = null;
                        String recipeURL = null;
                        String recipeTime = null;
                        String recipeRating = null;
//                        final List<String> dishTypes = new ArrayList<>();

                        for (final DataSnapshot ds : snap.getChildren()) {
//                            if (ds.getKey().toString().equals("Dish Types")) {
//                                reference.child("Dish Types").addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                        for (final DataSnapshot dishType : ds.getChildren()) {
//                                            dishTypes.add(dishType.getValue().toString());
//                                        }
//                                    }
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                    }
//                                });
//                            }
                            if (ds.getKey().toString().equals("Recipe Name")) {
                                recipeName = ds.getValue().toString();
                            } else if (ds.getKey().toString().equals("Recipe Time")) {
                                recipeTime = ds.getValue().toString();
                            } else if (ds.getKey().toString().equals("Recipe URL")) {
                                recipeURL = ds.getValue().toString();
                            } else if (ds.getKey().toString().equals("Recipe Rating")) {
                                recipeRating = ds.getValue().toString();
                            }
                        }

                        // on the off chance that spoonacular has some missing arguments
                        if (recipeName == null) {
                            recipeName = "";
                        }
                        if (recipeTime == null) {
                            recipeTime = "-1";
                        }
                        if (recipeURL == null) {
                            recipeURL = "";
                        }
                        if (recipeRating == null) {
                            recipeRating = "-1";
                        }

                        Recipe currRecipe = new Recipe(recipeID, recipeName, recipeURL, Integer.parseInt(recipeTime), Double.parseDouble(recipeRating));
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



    private void addListenerOnToggleButtonClick() {
        mealTypeToggleBtn.setOnCheckedChangeListener(handleOnClick(mealTypeToggleBtn));
        cuisineToggleBtn.setOnCheckedChangeListener(handleOnClick(cuisineToggleBtn));
        ratingToggleBtn.setOnCheckedChangeListener(handleOnClick(ratingToggleBtn));
        cookingTimeToggleBtn.setOnCheckedChangeListener(handleOnClick(cookingTimeToggleBtn));
    }

    CompoundButton.OnCheckedChangeListener handleOnClick(final ToggleButton button) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showPopupFilter(buttonView);
                }
            }
        };
    }

    private void showPopupFilter(final CompoundButton view) {
        // inflate the layout of the popup window
        inflater = getActivity().getLayoutInflater();
        popupView = inflater.inflate(R.layout.filter_popup_layout, null);
        popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        filter_recyclerview = popupView.findViewById(R.id.filter_recyclerview);
        selectFilterTextView = popupView.findViewById(R.id.select_filter_tv);
        exitFilterPopupImageView = popupView.findViewById(R.id.exit_button);
        applyFilterButton = popupView.findViewById(R.id.apply_filter_button);
        addListenerForPopUpView(view);
        setFilterAdapter(view);
    }

    private void addListenerForPopUpView(final CompoundButton view) {
        exitFilterPopupImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        // TODO: Filter favorite recipes according to the selectedFilters
        applyFilterButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (view.equals(mealTypeToggleBtn)) {
                    selectedFilters.put("Meal Type", filterAdapter.getSelectedFilters());
                } else if (view.equals(cookingTimeToggleBtn)) {
                    selectedFilters.put("Cooking Time", filterAdapter.getSelectedFilters());
                } else if (view.equals(cuisineToggleBtn)) {
                    selectedFilters.put("Cuisine", filterAdapter.getSelectedFilters());
                } else if (view.equals(ratingToggleBtn)) {
                    selectedFilters.put("Rating", filterAdapter.getSelectedFilters());
                }
                applyFilter();
                view.setChecked(true);
                popupWindow.dismiss();
                return true;
            }
        });
    }

    private void setFilterAdapter(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        filter_recyclerview.setLayoutManager(layoutManager);

        if (view.equals(mealTypeToggleBtn)) {
            selectFilterTextView.setText("Select Meal Type");
            filterAdapter = new FilterAdapter(context, filters.getMealTypeOptions(), selectedFilters.get("Meal Type"));
        } else if (view.equals(cookingTimeToggleBtn)) {
            selectFilterTextView.setText("Select Cooking Time");
            filterAdapter = new FilterAdapter(context, filters.getCookingTimeOptions(), selectedFilters.get("Cooking Time"));
        } else if (view.equals(cuisineToggleBtn)) {
            selectFilterTextView.setText("Select Cuisine");
            filterAdapter = new FilterAdapter(context, filters.getCuisineOptions(), selectedFilters.get("Cuisine"));
        } else if (view.equals(ratingToggleBtn)) {
            selectFilterTextView.setText("Select Rating");
            filterAdapter = new FilterAdapter(context, filters.getRatingOptions(), selectedFilters.get("Rating"));
        }
        filter_recyclerview.setAdapter(filterAdapter);
    }
}