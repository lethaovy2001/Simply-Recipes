package com.example.simplyrecipes.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

class CurrentUserTest {
    private CurrentUser testUser;
    private String userUID;
    private String userEmail;
    private List<Recipe> userRecipeList;
    private int recipeID_1, recipeID_2;
    private String recipe_name_1, recipe_name_2;
    private String image_1, image_2;
    private int recipe_time_1, recipe_time_2;
    private Recipe recipe_1, recipe_2;
    private double recipe_rating_1, recipe_rating_2;

    @BeforeEach
    void setUp() {
        userUID = "testUser";
        userEmail = "testUser@gmail.com";
        recipeID_1 = 1;
        recipe_name_1 = "ratatouille";
        image_1 = "0";
        recipe_time_1 = 2;
        recipeID_2 = 2;
        recipe_name_2 = "remy";
        image_2 = "0";
        recipe_time_2 = 1;
        recipe_rating_1 = 3.6;
        recipe_rating_2 = 4.2;
        recipe_1 = new Recipe(recipeID_1, recipe_name_1, image_1, recipe_time_1, recipe_rating_1);
        recipe_2 = new Recipe(recipeID_2, recipe_name_2, image_2, recipe_time_2, recipe_rating_2);
        userRecipeList = new ArrayList<Recipe>();
        userRecipeList.add(recipe_1);
        userRecipeList.add(recipe_2);
        testUser = new CurrentUser(userUID, userEmail, userRecipeList);
    }

    @AfterEach
    void tearDown() {
        recipe_1 = null;
        recipe_2 = null;
        userRecipeList = null;
    }

    @Test
    void getUserUIDTest() {
        assertEquals("testUser", testUser.getUserUID());
    }

    @Test
    void getUserEmailTest() {
        assertEquals("testUser@gmail.com", testUser.getEmail());
    }

    @Test
    void getUserFavoriteRecipes() {
        List<Recipe> testList = Arrays.asList(recipe_1, recipe_2);
        assertEquals(testList, testUser.getFavoriteRecipes());
    }
}
