package com.example.simplyrecipes.Activities;


import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ReportFragment;

import com.example.simplyrecipes.R;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
public class RecipeDetailActivityTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testRecipe() {
        RecipeDetailActivity mActivity = spy(new RecipeDetailActivity());
        doNothing().when(mActivity).setContentView(R.layout.recipe_detail_page);
        doReturn(mock(AppCompatDelegate.class)).when(mActivity).getDelegate();
    }

    @Test
    public void testTest() {
        RecipeDetailActivity test = mock(RecipeDetailActivity.class);
        when(test.getIngredientSize()).thenReturn(5);
        when(test.getRecipeID()).thenReturn(1);

        assertEquals(test.getIngredientSize(), 5);
        assertEquals(test.getRecipeID(), 1);
    }


}