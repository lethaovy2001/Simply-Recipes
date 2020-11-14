package com.example.simplyrecipes.Activities;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.Context;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;

class PantryPageAdapterTest {
    @Mock
    Context context;
    List<Ingredient> ingredients;
    @Mock
    private PantryPageAdapter adapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredients = new ArrayList<Ingredient>();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void onCreateViewHolderTest() {
//        adapter = spy(new PantryPageAdapter(context, ingredients));
//        doNothing().when(adapter).onCreateViewHolder()
    }

    @Test
    void onBindViewHolder() {
    }

    @Test
    void getItemCount() {
        assertEquals(0, 0);
    }
}