package com.example.simplyrecipes.Activities;


import android.content.Intent;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.simplyrecipes.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4ClassRunner.class)
public class SignUpActivityTest {
    @Rule
    public IntentsTestRule<SignUpActivity> mActivity =
            new IntentsTestRule<SignUpActivity>(SignUpActivity.class) {};

    @Test
    public void ensureTextChangesWork() {
        // Type text and then press the button.
        onView(withId(R.id.editTextUsername)).perform(typeText("test"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextUsername))
                .check(matches(withText("test")));

        onView(withId(R.id.editTextTextEmailAddress)).perform(typeText("testUI@gmail.com"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextTextEmailAddress))
                .check(matches(withText("testUI@gmail.com")));

        onView(withId(R.id.editTextTextPassword)).perform(typeText("testUI"),
                closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword))
                .check(matches(withText("testUI")));

        onView(withId(R.id.sign_up_button)).perform(click());
    }

    @Test
    public void changeText_newActivity() {
        onView(withId(R.id.sign_in_button)).perform(click());
        intended(hasComponent(SignInActivity.class.getName()));
    }

}