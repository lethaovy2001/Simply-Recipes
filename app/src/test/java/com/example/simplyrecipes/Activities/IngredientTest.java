package com.example.simplyrecipes.Activities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {
    private Ingredient detailsIngredient;
    private Ingredient shoppingIngredient;
    private Ingredient basicIngredient;
    private int ingredientID;
    private String ingredientAmount;
    private String ingredientName;
    private String ingredientCategory;
    @BeforeEach
    void setUp() {
        ingredientID = 1;
        ingredientAmount = "1 teaspoon";
        ingredientName = "salt";
        ingredientCategory = "spice";
        detailsIngredient = new Ingredient(ingredientID, ingredientAmount, ingredientName, ingredientCategory);
        shoppingIngredient = new Ingredient(ingredientID, ingredientName, ingredientCategory);
        basicIngredient = new Ingredient(ingredientName);
    }

    @AfterEach
    void tearDown() {
        detailsIngredient = null;
        shoppingIngredient = null;
        basicIngredient = null;
    }

    @Test
    void isInShoppingCartTest() {
        assertFalse(detailsIngredient.isInShoppingCart());
    }

    @Test
    void setInShoppingCartTest() {
        detailsIngredient.setInShoppingCart(true);
        assertTrue(detailsIngredient.isInShoppingCart());
    }

    @Test
    void getIngredientIDTest() {
        assertEquals(ingredientID, detailsIngredient.getIngredientID());
    }

    @Test
    void setIngredientIDTest() {
        detailsIngredient.setIngredientID(99);
        assertEquals(99, detailsIngredient.getIngredientID());
    }

    @Test
    void getIngredientCategoryTest() {
        assertEquals(ingredientCategory, detailsIngredient.getIngredientCategory());
    }

    @Test
    void setIngredientCategoryTest() {
        detailsIngredient.setIngredientCategory("grain");
        assertEquals("grain", detailsIngredient.getIngredientCategory());
    }

    @Test
    void getIngredientAmountTest() {
        assertEquals(ingredientAmount, detailsIngredient.getIngredientAmount());
    }

    @Test
    void setIngredientAmountTest() {
        detailsIngredient.setIngredientAmount("1 gallon");
        assertEquals("1 gallon", detailsIngredient.getIngredientAmount());
    }

    @Test
    void getIngredientNameTest() {
        assertEquals(ingredientName, detailsIngredient.getIngredientName());
    }

    @Test
    void setIngredientNameTest() {
        detailsIngredient.setIngredientName("rice");
        assertEquals("rice", detailsIngredient.getIngredientName());
    }
}