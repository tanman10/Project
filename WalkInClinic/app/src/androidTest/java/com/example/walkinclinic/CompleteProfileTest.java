package com.example.walkinclinic;

import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
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

public class CompleteProfileTest {
    @Rule
    public ActivityTestRule<createClinic> mActivityTestRule = new ActivityTestRule<createClinic>(createClinic.class);

    @Test
    public void completeProfileIsValid() {
        onView(withId(R.id.clinicName)).perform(typeText("clinic name"), closeSoftKeyboard());
        onView(withId(R.id.clinicAddress)).perform(typeText("fake address"), closeSoftKeyboard());
        onView(withId(R.id.clinicPhoneNum)).perform(typeText("0123456789"), closeSoftKeyboard());
        onView(withId(R.id.clinicInsurance)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Life Insurance"))).perform(click());
        onView(withId(R.id.clinicInsurance)).check(matches(withSpinnerText(containsString("Life Insurance"))));

        onView(withId(R.id.clinicPayment)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Cash Only"))).perform(click());
        onView(withId(R.id.clinicPayment)).check(matches(withSpinnerText(containsString("Cash Only"))));

        onData(anything()).inAdapterView(withId(R.id.addAService)).atPosition(1).perform(longClick());


        onView(withId(R.id.startHM1)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("08"))).perform(click());
        onView(withId(R.id.startHM1)).check(matches(withSpinnerText(containsString("08"))));
        onView(withId(R.id.startMM1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.startMM1)).check(matches(withSpinnerText(containsString("00"))));
        onView(withId(R.id.endHM1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("22"))).perform(click());
        onView(withId(R.id.endHM1)).check(matches(withSpinnerText(containsString("22"))));
        onView(withId(R.id.endMM1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.endMM1)).check(matches(withSpinnerText(containsString("00"))));


        onView(withId(R.id.startHTU1)).perform(scrollTo(),click());
        onData(allOf(is(instanceOf(String.class)), is("08"))).perform(click());
        onView(withId(R.id.startHTU1)).check(matches(withSpinnerText(containsString("08"))));
        onView(withId(R.id.startMTU1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.startMTU1)).check(matches(withSpinnerText(containsString("00"))));
        onView(withId(R.id.endHTU1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("22"))).perform(click());
        onView(withId(R.id.endHTU1)).check(matches(withSpinnerText(containsString("22"))));
        onView(withId(R.id.endMTU1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.endMTU1)).check(matches(withSpinnerText(containsString("00"))));



        onView(withId(R.id.startHW1)).perform(scrollTo(),click());
        onData(allOf(is(instanceOf(String.class)), is("08"))).perform(click());
        onView(withId(R.id.startHW1)).check(matches(withSpinnerText(containsString("08"))));
        onView(withId(R.id.startMW1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.startMW1)).check(matches(withSpinnerText(containsString("00"))));
        onView(withId(R.id.endHW1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("22"))).perform(click());
        onView(withId(R.id.endHW1)).check(matches(withSpinnerText(containsString("22"))));
        onView(withId(R.id.endMW1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.endMW1)).check(matches(withSpinnerText(containsString("00"))));



        onView(withId(R.id.startHTH1)).perform(scrollTo(),click());
        onData(allOf(is(instanceOf(String.class)), is("08"))).perform(click());
        onView(withId(R.id.startHTH1)).check(matches(withSpinnerText(containsString("08"))));
        onView(withId(R.id.startMTH1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.startMTH1)).check(matches(withSpinnerText(containsString("00"))));
        onView(withId(R.id.endHTH1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("22"))).perform(click());
        onView(withId(R.id.endHTH1)).check(matches(withSpinnerText(containsString("22"))));
        onView(withId(R.id.endMTH1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.endMTH1)).check(matches(withSpinnerText(containsString("00"))));



        onView(withId(R.id.startHF1)).perform(scrollTo(),click());
        onData(allOf(is(instanceOf(String.class)), is("08"))).perform(click());
        onView(withId(R.id.startHF1)).check(matches(withSpinnerText(containsString("08"))));
        onView(withId(R.id.startMF1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.startMF1)).check(matches(withSpinnerText(containsString("00"))));
        onView(withId(R.id.endHF1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("22"))).perform(click());
        onView(withId(R.id.endHF1)).check(matches(withSpinnerText(containsString("22"))));
        onView(withId(R.id.endMF1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.endMF1)).check(matches(withSpinnerText(containsString("00"))));



        onView(withId(R.id.startHSA1)).perform(scrollTo(),click());
        onData(allOf(is(instanceOf(String.class)), is("08"))).perform(click());
        onView(withId(R.id.startHSA1)).check(matches(withSpinnerText(containsString("08"))));
        onView(withId(R.id.startMSA1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.startMSA1)).check(matches(withSpinnerText(containsString("00"))));
        onView(withId(R.id.endHSA1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("22"))).perform(click());
        onView(withId(R.id.endHSA1)).check(matches(withSpinnerText(containsString("22"))));
        onView(withId(R.id.endMSA1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.endMSA1)).check(matches(withSpinnerText(containsString("00"))));


        onView(withId(R.id.startHSU1)).perform(scrollTo(),click());
        onData(allOf(is(instanceOf(String.class)), is("08"))).perform(click());
        onView(withId(R.id.startHSU1)).check(matches(withSpinnerText(containsString("08"))));
        onView(withId(R.id.startMSU1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.startMSU1)).check(matches(withSpinnerText(containsString("00"))));
        onView(withId(R.id.endHSU1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("22"))).perform(click());
        onView(withId(R.id.endHSU1)).check(matches(withSpinnerText(containsString("22"))));
        onView(withId(R.id.endMSU1)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.endMSU1)).check(matches(withSpinnerText(containsString("00"))));

        onView(withId(R.id.createClinic)).perform(scrollTo(),click());


        onView(withText("Clinic created")).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }
}
