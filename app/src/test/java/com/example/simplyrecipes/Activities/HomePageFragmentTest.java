package com.example.simplyrecipes.Activities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HomePageFragmentTest{
    private String favoriteRecipeURL = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/random?number=10&limitLicense=false";
    private enum recipeType{
      Asian, Western, Mediterranean, Popular
    };
    private HomePageFragment hpfrag;
    private List<Recipe> recipeList;
    @BeforeEach
    public void setUp(){
        hpfrag = new HomePageFragment();
        recipeList = new ArrayList<Recipe>();
    }
    // Tests if the returned list of recipe from the API has the right amount of recipes
    @Test
    public void extractPopularRecipesTest() throws Exception{
        hpfrag.extractRecipes(favoriteRecipeURL, recipeList, recipeType.Popular.toString());
        //The size of the list of recipes should be limited to 15.
        assertTrue(hpfrag.extractRecipes(favoriteRecipeURL, recipeList, recipeType.Popular.toString()));
    }

    // Tests if the returned list of recipe from the API has the right amount of recipes
    @Test
    public void extractWesternRecipesTest() throws Exception{
        hpfrag.extractRecipes(favoriteRecipeURL, recipeList, recipeType.Popular.toString());
        //The size of the list of recipes should be limited to 15.
        assertTrue(hpfrag.extractRecipes(favoriteRecipeURL, recipeList, recipeType.Western.toString()));
    }

    // Tests if the returned list of recipe from the API has the right amount of recipes
    @Test
    public void extractAsianRecipesTest() throws Exception{
        hpfrag.extractRecipes(favoriteRecipeURL, recipeList, recipeType.Popular.toString());
        //The size of the list of recipes should be limited to 15.
        assertTrue(hpfrag.extractRecipes(favoriteRecipeURL, recipeList, recipeType.Asian.toString()));
    }

    // Tests if the returned list of recipe from the API has the right amount of recipes
    @Test
    public void extractMediterraneanRecipesTest() throws Exception{
        hpfrag.extractRecipes(favoriteRecipeURL, recipeList, recipeType.Popular.toString());
        //The size of the list of recipes should be limited to 15.
        assertTrue(hpfrag.extractRecipes(favoriteRecipeURL, recipeList, recipeType.Mediterranean.toString()));
    }
    @Test
    public void getFragmentTagTest() throws Exception{
        assertEquals("HomePageFragment", hpfrag.getFragmentTag());
    }
    @AfterEach
    public void tearDown() throws Exception{
        hpfrag = null;
        recipeList = null;
    }
}