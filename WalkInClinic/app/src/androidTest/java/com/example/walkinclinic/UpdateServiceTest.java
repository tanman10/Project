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
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

public class UpdateServiceTest {

    @Rule
    public ActivityTestRule<AdminProfileActivity> mActivityTestRule = new ActivityTestRule<AdminProfileActivity>(AdminProfileActivity.class);

    @Test
    public void UpdateProductIsValid() {
        onView(withId(R.id.editTextName)).perform(typeText("waiting for synchronous"), closeSoftKeyboard());
        onData(anything()).inAdapterView(withId(R.id.listViewProducts)).atPosition(3).perform(longClick());
        onView(withId(R.id.editTextName)).perform(typeText("Updated service"), closeSoftKeyboard());

        onView(withId(R.id.spinner2)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Nurse"))).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.spinner2)).check(matches(withSpinnerText(containsString("Nurse"))));


        onView(withId(R.id.buttonUpdateProduct)).perform(click());
        onView(withText("Service Updated")).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }
}
