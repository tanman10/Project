package com.example.walkinclinic;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

public class CreateUserTest {

    // Register your Idling Resource before any tests regarding this component
    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @Rule
    public ActivityTestRule<CreateAccountActivity> mActivityTestRule = new ActivityTestRule<CreateAccountActivity>(CreateAccountActivity.class);

    @Test
    public void createAccountIsValid() throws Exception{
        onView(withId(R.id.signUpfirstNameTextField)).perform(typeText("last"), closeSoftKeyboard());
        onView(withId(R.id.signUplastNameTextField)).perform(typeText("name"), closeSoftKeyboard());
        onView(withId(R.id.signUpemailTextField)).perform(typeText("lastname9@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.signUppasswordTextField)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.signUproleTextField)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employee"))).perform(click());
        onView(withId(R.id.signUproleTextField)).check(matches(withSpinnerText(containsString("Employee"))));
        onView(withId(R.id.signUpButton)).perform(click());
        onView(withText("Account created")).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }


    // Unregister your Idling Resource so it can be garbage collected and does not leak any memory
    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

}
