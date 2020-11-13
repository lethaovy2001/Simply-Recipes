package com.example.simplyrecipes.Activities;

import android.os.Bundle;
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
import java.util.List;


public class FavoriteFragment extends Fragment {

    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseAuth auth;
    List<Recipe> favoriteRecipes;
    Filter filters;
    RecyclerView favorite_recipe_recyclerview, filter_recyclerview;
    FavoriteAdapter adapter;
    FilterAdapter filterAdapter;
    ToggleButton mealTypeToggleBtn, cuisineToggleBtn, cookingTimeToggleBtn, ratingToggleBtn;
    TextView selectFilterTextView;
    ImageView exitFilterPopupImageView;
    PopupWindow popupWindow;
    Button applyFilterButton;


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
        getFavoriteRecipes();
        addListenerOnToggleButtonClick();
    }


    private void getFavoriteRecipes() {
        reference = FirebaseDatabase.getInstance().getReference("users/"+auth.getCurrentUser().getUid()+"/Favorite");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoriteRecipes.clear();
                for(DataSnapshot snap : snapshot.getChildren()) {

                    if(!snap.getKey().toString().equals("none")) {
                        int recipeID = Integer.parseInt(snap.getKey().toString());
                        String recipeName = null;
                        String recipeURL = null;
                        String recipeTime = null;
                        for(DataSnapshot ds: snap.getChildren()) {
                            if(ds.getKey().toString().equals("Recipe Name")) {
                                recipeName = ds.getValue().toString();

                            }
                            else if (ds.getKey().toString().equals("Recipe Time")) {
                                recipeTime = ds.getValue().toString();

                            }
                            else if(ds.getKey().toString().equals("Recipe URL")) {
                                recipeURL = ds.getValue().toString();

                            }
                        }
                        // on the off chance that spoonacular has some missing arguments
                        if(recipeName == null) {
                            recipeName = "";
                        }
                        if(recipeTime == null) {
                            recipeTime = "-1";
                        }
                        if(recipeURL == null) {
                            recipeURL = "";
                        }

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

    public void addListenerOnToggleButtonClick(){
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
                } else {

                }
            }
        };
    }

    public void showPopupFilter(final CompoundButton view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View popupView = inflater.inflate(R.layout.filter_popup_layout, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        selectFilterTextView = popupView.findViewById(R.id.select_filter_tv);
        exitFilterPopupImageView = popupView.findViewById(R.id.exit_button);
        applyFilterButton = popupView.findViewById(R.id.apply_filter_button);
        filter_recyclerview = popupView.findViewById(R.id.filter_recyclerview);

        exitFilterPopupImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        applyFilterButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                view.setChecked(true);
                popupWindow.dismiss();
                return true;
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        filter_recyclerview.setLayoutManager(layoutManager);

        if (view.equals(mealTypeToggleBtn)) {
            selectFilterTextView.setText("Select Meal Type");
            filterAdapter = new FilterAdapter(getActivity().getApplicationContext(), filters.getMealTypeOptions());
            filter_recyclerview.setAdapter(filterAdapter);
        } else if (view.equals(cookingTimeToggleBtn)) {
            selectFilterTextView.setText("Select Cooking Time");
            filterAdapter = new FilterAdapter(getActivity().getApplicationContext(), filters.getCookingTimeOptions());
            filter_recyclerview.setAdapter(filterAdapter);
        } else if (view.equals(cuisineToggleBtn)) {
            selectFilterTextView.setText("Select Cuisine");
            filterAdapter = new FilterAdapter(getActivity().getApplicationContext(), filters.getCuisineOptions());
            filter_recyclerview.setAdapter(filterAdapter);
        } else if (view.equals(ratingToggleBtn)) {
            selectFilterTextView.setText("Select Rating");
            filterAdapter = new FilterAdapter(getActivity().getApplicationContext(), filters.getRatingOptions());
            filter_recyclerview.setAdapter(filterAdapter);
        }
    }
}
