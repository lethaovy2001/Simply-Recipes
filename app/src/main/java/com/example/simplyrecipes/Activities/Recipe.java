package com.example.simplyrecipes.Activities;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private int recipeID;
    private String title;
    private String image;
    private double recipeRating;
    private String url;
    private int recipeTime;
    private List<String> dishTypes;

    public int getRecipeTime() {
        return this.recipeTime;
    }

    public void setRecipeTime(int recipeTime) {
        this.recipeTime = recipeTime;
    }


    public double getRecipeRating() {
        return recipeRating;
    }

    public void setRecipeRating(double recipeRating) {
        this.recipeRating = recipeRating;
    }

    // for storing recipes in the home page
    public Recipe(int recipeID, String title, String image, double recipeRating, String url) {
        this.recipeID = recipeID;
        this.title = title;
        this.image = image;
        this.recipeRating = recipeRating;
        this.url = url;
    }

    // for storing in recipes in favorites (only need recipeID and recipeName
    public Recipe(int recipeID, String recipeName, String image, int recipeTime, double recipeRating) {
        this.recipeID = recipeID;
        this.title = recipeName;
        this.image = image;
        this.recipeTime = recipeTime;
        this.recipeRating = recipeRating;
    }

    public List<String> getDishTypes() { return dishTypes; }

    public void setDishTypes(List<String> dishTypes) { this.dishTypes = dishTypes; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
