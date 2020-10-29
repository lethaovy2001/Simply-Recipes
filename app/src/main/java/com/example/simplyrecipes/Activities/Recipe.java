package com.example.simplyrecipes.Activities;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String title;
    private String image;
    private String url;
    private List<String> ingredientLines = new ArrayList<>();
    private String calories;
    private String servings;
    private String ratings;

    public Recipe(String title, String image, String url, ArrayList<String> ingredientLines, String calories, String servings) {

        this.title = title;
        this.image = image;
        this.url = url;
        this.ingredientLines = ingredientLines;
        this.calories = calories;
        this.servings = servings;
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

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getIngredientLines() {
        return ingredientLines;
    }

    public void setIngredientLines(List<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }
}
