package com.example.simplyrecipes.Activities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {
    private Recipe homePageRecipe;
    private Recipe favoriteRecipe;
    private int recipeID;
    private String title;
    private String recipeName;
    private String image;
    private double recipeRating;
    private String url;
    private int recipeTime;
    private List<String> dishTypes, cuisines;

    @BeforeEach
    void setUp() {
        recipeID = 1;
        title = "ratatouille";
        recipeName = "ratatouille";
        image = "0";
        recipeRating = 5.0;
        url = "ratatouille.com";
        recipeTime = 2;
        homePageRecipe = new Recipe(recipeID, title, image, recipeRating, url);
        favoriteRecipe = new Recipe(recipeID, recipeName, image, recipeTime, recipeRating);

        dishTypes = new ArrayList<String>();
        cuisines = new ArrayList<String>();
        dishTypes.add("breakfast");
        cuisines.add("Asian");

        homePageRecipe.setDishTypes(dishTypes);
        homePageRecipe.setCuisines(cuisines);
    }

    @AfterEach
    void tearDown() {
        homePageRecipe = null;
        favoriteRecipe = null;
    }

    @Test
    void getRecipeTimeTest() {
        assertEquals(2, favoriteRecipe.getRecipeTime());
    }

    @Test
    void setRecipeTimeTest() {
        favoriteRecipe.setRecipeTime(3);
        assertEquals(3, favoriteRecipe.getRecipeTime());
    }

    @Test
    void getRecipeRatingTest() {
        assertEquals(5.0, homePageRecipe.getRecipeRating());
    }

    @Test
    void setRecipeRatingTest() {
        homePageRecipe.setRecipeRating(4.0);
        assertEquals(4.0, homePageRecipe.getRecipeRating());
    }

    @Test
    void getTitleTest() {
        assertEquals("ratatouille", homePageRecipe.getTitle());
    }

    @Test
    void setTitleTest() {
        homePageRecipe.setTitle("Cream stew");
        assertEquals("Cream stew", homePageRecipe.getTitle());
    }

    @Test
    void getImageTest() {
        assertEquals("0", homePageRecipe.getImage());
    }

    @Test
    void getRecipeIDTest() {
        assertEquals(1, homePageRecipe.getRecipeID());
    }

    @Test
    void setRecipeIDTest() {
        homePageRecipe.setRecipeID(10);
        assertEquals(10, homePageRecipe.getRecipeID());
    }

    @Test
    void setImageTest() {
        homePageRecipe.setImage("12");
        assertEquals("12", homePageRecipe.getImage());
    }

    @Test
    void getUrlTest() {
        assertEquals("ratatouille.com", homePageRecipe.getUrl());
    }

    @Test
    void setUrlTest() {
        homePageRecipe.setUrl("stew.com");
        assertEquals("stew.com", homePageRecipe.getUrl());
    }

    @Test
    void getDishTypesTest() {
        assertEquals(dishTypes, homePageRecipe.getDishTypes());
    }

    @Test
    void setDishTypesTest() {
        homePageRecipe.setDishTypes(dishTypes);
        assertEquals(dishTypes, homePageRecipe.getDishTypes());
    }

    @Test
    void getCuisinesTest() {
        assertEquals(cuisines, homePageRecipe.getCuisines());
    }

    @Test
    void setCuisinesTest() {
        homePageRecipe.setCuisines(cuisines);
        assertEquals(cuisines, homePageRecipe.getCuisines());
    }
}