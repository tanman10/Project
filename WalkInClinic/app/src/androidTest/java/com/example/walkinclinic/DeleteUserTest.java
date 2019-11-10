package com.example.walkinclinic;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNot.not;

public class DeleteUserTest {

    @Rule
    public ActivityTestRule<practice> mActivityTestRule = new ActivityTestRule<practice>(practice.class);

    @Test
    public void RemoveUserIsValid() {
        onView(withId(R.id.editTextDelay)).perform(typeText("waiting for synchronous"), closeSoftKeyboard());
        onData(anything()).inAdapterView(withId(R.id.listViewPerson)).atPosition(7).perform(longClick());


        onView(withId(R.id.buttonDeleteProduct)).perform(click());
        onView(withText("User Deleted")).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}
