package com.example.simplyrecipes.Activities;

import java.util.List;

public class CurrentUser {
    String userUID, email;
    List<Recipe> favoriteRecipes;
    List<Ingredient> shoppingList;

    public CurrentUser(String userUID, String email, List<Recipe> favoriteRecipes) {
        this.userUID = userUID;
        this.email = email;
        this.favoriteRecipes = favoriteRecipes;
        //this.shoppingList = shoppingList;
    }

    public String getUserUID() {
        return this.userUID;
    }

    public String getEmail() {
        return this.userUID;
    }

    public List<Recipe> getFavoriteRecipes() {
        return this.favoriteRecipes;
    }
}
