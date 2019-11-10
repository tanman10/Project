package com.example.walkinclinic;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityTest2 {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new
            ActivityTestRule<>(MainActivity.class);
    @Test
    public void emailIsInvalid() {
        onView(withId(R.id.passwordTextField)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.emailTextField)).perform(typeText("email@"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.emailTextField)).check(matches(hasErrorText("Please enter a valid email")));
    }
}
