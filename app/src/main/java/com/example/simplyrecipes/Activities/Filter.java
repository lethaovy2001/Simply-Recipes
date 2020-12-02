package com.example.simplyrecipes.Activities;

import java.util.ArrayList;
import java.util.List;

public class Filter {
    public Filter() { }

    public List<String> getMealTypeOptions() {
        List<String> options = new ArrayList<>();
        options.add("Breakfast");
        options.add("Brunch");
        options.add("Main Dish");
        options.add("Lunch");
        options.add("Dinner");
        options.add("Side Dish");
        return options;
    }

    public List<String> getCookingTimeOptions() {
        List<String> options = new ArrayList<>();
        options.add("Less than 15 minutes");
        options.add("15 - 30 minutes");
        options.add("30 - 60 minutes");
        options.add("60 - 120 minutes");
        options.add("More than 120 minutes");
        return options;
    }

    public List<String> getRatingOptions() {
        List<String> options = new ArrayList<>();
        options.add("4.0 - 5.0");
        options.add("3.0 - 4.0");
        options.add("2.0 - 3.0");
        options.add("1.0 - 2.0");
        return options;
    }

    public List<String> getCuisineOptions() {
        List<String> options = new ArrayList<>();
        options.add("Chinese");
        options.add("Mexican");
        options.add("Korean");
        options.add("Vietnamese");
        options.add("Indian");
        options.add("Caribbean");
        options.add("Indian");
        options.add("Thai");
        options.add("Japanese");
        return options;
    }
}
