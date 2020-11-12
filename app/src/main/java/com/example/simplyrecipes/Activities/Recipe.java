package com.example.simplyrecipes.Activities;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private int recipeID;
    private String title;
    private String image;
    private double recipeRating;
    private String url;

    public double getRecipeRating() {
        return recipeRating;
    }

    public void setRecipeRating(double recipeRating) {
        this.recipeRating = recipeRating;
    }

    public Recipe(int recipeID, String title, String image, double recipeRating) {
        this.recipeID = recipeID;
        this.title = title;
        this.image = image;
        this.recipeRating = recipeRating;

    }
    // for storing in recipes in favorites (only need recipeID and recipeName
    public Recipe(int recipeID, String recipeName) {
        this.recipeID = recipeID;
        this.title = recipeName;
    }

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
