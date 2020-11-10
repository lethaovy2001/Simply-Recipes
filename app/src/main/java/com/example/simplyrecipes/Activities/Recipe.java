package com.example.simplyrecipes.Activities;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private int recipeID;
    private String title;
    private String image;
    private String url;

    public Recipe(int recipeID, String title, String image) {
        this.recipeID = recipeID;
        this.title = title;
        this.image = image;

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
