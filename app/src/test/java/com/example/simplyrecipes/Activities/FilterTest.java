package com.example.simplyrecipes.Activities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterTest {
    private List<String> mealTypeList;
    private List<String> cookingTimeList;
    private List<String> cuisineList;
    private List<String> ratingList;
    private Filter filters;

    @BeforeEach
    void setUp() {
        mealTypeList = new ArrayList<>();
        cookingTimeList = new ArrayList<>();
        cuisineList = new ArrayList<>();
        ratingList = new ArrayList<>();
        filters = new Filter();
    }

    @AfterEach
    void tearDown() {
        filters = null;
        mealTypeList = null;
        cookingTimeList = null;
        cuisineList = null;
        ratingList = null;
    }

    @Test
    void getMealTypeOptions() {
        mealTypeList.add("Breakfast");
        mealTypeList.add("Brunch");
        mealTypeList.add("Lunch");
        mealTypeList.add("Supper");
        mealTypeList.add("Dinner");
        assertEquals(mealTypeList, filters.getMealTypeOptions());
    }

    @Test
    void getCookingTimeOptions() {
        cookingTimeList.add("Less than 15 minutes");
        cookingTimeList.add("15 minutes - 30 minutes");
        cookingTimeList.add("30 minutes - 1 hour");
        cookingTimeList.add("1 hour - 2 hours ");
        cookingTimeList.add("More than 2 hours");
        assertEquals(cookingTimeList, filters.getCookingTimeOptions());
    }

    @Test
    void getRatingOptions() {
        ratingList.add("4.0 - 5.0");
        ratingList.add("3.0 - 4.0");
        ratingList.add("2.0 - 1.0");
        assertEquals(ratingList, filters.getRatingOptions());
    }

    @Test
    void getCuisineOptions() {
        cuisineList.add("Chinese");
        cuisineList.add("Mexican");
        cuisineList.add("Korean");
        cuisineList.add("Vietnamese");
        cuisineList.add("Indian");
        cuisineList.add("Caribbean");
        cuisineList.add("Indian");
        cuisineList.add("Thai");
        cuisineList.add("Japanese");
        assertEquals(cuisineList, filters.getCuisineOptions());
    }
}