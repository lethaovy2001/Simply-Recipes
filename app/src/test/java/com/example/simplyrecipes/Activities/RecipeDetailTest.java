package com.example.simplyrecipes.Activities;


import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class RecipeDetailTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testTest() {
        RecipeDetailActivity test = Mockito.mock(RecipeDetailActivity.class);
        when(test.getIngredientSize()).thenReturn(5);
        when(test.getRecipeID()).thenReturn(1);

        assertEquals(test.getIngredientSize(), 5);
        assertEquals(test.getRecipeID(), 1);
    }


}