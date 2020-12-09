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
        mealTypeList.add("Main Dish");
        mealTypeList.add("Lunch");
        mealTypeList.add("Dinner");
        mealTypeList.add("Side Dish");
        assertEquals(mealTypeList, filters.getMealTypeOptions());
    }

    @Test
    void getCookingTimeOptions() {
        cookingTimeList.add("Less than 15 minutes");
        cookingTimeList.add("15 - 30 minutes");
        cookingTimeList.add("30 - 60 minutes");
        cookingTimeList.add("60 - 120 minutes");
        cookingTimeList.add("More than 120 minutes");
        assertEquals(cookingTimeList, filters.getCookingTimeOptions());
    }

    @Test
    void getRatingOptions() {
        ratingList.add("4.0 - 5.0");
        ratingList.add("3.0 - 4.0");
        ratingList.add("2.0 - 3.0");
        ratingList.add("1.0 - 2.0");
        assertEquals(ratingList, filters.getRatingOptions());
    }

    @Test
    void getCuisineOptions() {
        cuisineList.add("Chinese");
        cuisineList.add("Mexican");
        cuisineList.add("Italian");
        cuisineList.add("European");
        cuisineList.add("Indian");
        cuisineList.add("Caribbean");
        cuisineList.add("Mediterranean");
        cuisineList.add("Asian");
        cuisineList.add("Japanese");
        assertEquals(cuisineList, filters.getCuisineOptions());
    }
}