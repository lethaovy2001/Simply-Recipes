package com.example.simplyrecipes.Activities;

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.android21buttons.fragmenttestrule.FragmentTestRule;
import com.example.simplyrecipes.R;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class HomePageFragmentTest {
    @Rule
    public FragmentTestRule<?, HomePageFragment> fragmentTestRule =
            FragmentTestRule.create(HomePageFragment.class);

    @Test
    public void clickButton() throws Exception {

    }
}
