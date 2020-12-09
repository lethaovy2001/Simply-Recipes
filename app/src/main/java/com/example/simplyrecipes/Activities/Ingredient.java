package com.example.simplyrecipes.Activities;

public class Ingredient {
    private int ingredientID;
    private String ingredientAmount;
    private String ingredientName;
    private String ingredientCategory;
    private boolean inShoppingCart;

    // for the recipe details page
    public Ingredient(int ingredientID, String ingredientAmount, String ingredientName, String ingredientCategory) {
        this.ingredientID = ingredientID;
        this.ingredientAmount = ingredientAmount;
        this.ingredientName = ingredientName;
        this.ingredientCategory = ingredientCategory;
        this.inShoppingCart = false;
    }

    // for the shopping list page
    public Ingredient(int ingredientID, String ingredientName, String ingredientCategory) {
        this.ingredientID = ingredientID;
        this.ingredientName = ingredientName;
        this.ingredientCategory = ingredientCategory;
    }

    public boolean isInShoppingCart() {
        return inShoppingCart;
    }

    public void setInShoppingCart(boolean inShoppingCart) {
        this.inShoppingCart = inShoppingCart;
    }

    // for storing ingredients for shopping cart
    public Ingredient(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }

    public String getIngredientCategory() {
        return ingredientCategory;
    }

    public void setIngredientCategory(String ingredientCategory) {
        this.ingredientCategory = ingredientCategory;
    }

    public String getIngredientAmount() {
        return ingredientAmount;
    }

    public void setIngredientAmount(String ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
}
