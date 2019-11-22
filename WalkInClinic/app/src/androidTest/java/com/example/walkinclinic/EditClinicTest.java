package com.example.walkinclinic;

import android.view.Gravity;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.contrib.DrawerMatchers.isOpen;
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

public class EditClinicTest {



    @Rule
    public ActivityTestRule<ProfilePatientActivity> mActivityTestRule = new ActivityTestRule<ProfilePatientActivity>(ProfilePatientActivity.class);

    @Test
    public void editProfileIsValid() throws Exception{

        onView(ViewMatchers.withId(R.id.drawable_layout2)).perform(DrawerActions.open());
        onView(withId(R.id.navigationView2)).perform(NavigationViewActions.navigateTo(R.id.edit));
        onView(ViewMatchers.withId(R.id.drawable_layout2)).perform(DrawerActions.close());

        onView(withId(R.id.namesync)).perform(scrollTo(), typeText("clinic name"), closeSoftKeyboard());
        onView(withId(R.id.addresssync)).perform(typeText("fake address"), closeSoftKeyboard());
        onView(withId(R.id.phonesync)).perform(typeText("0123456789"), closeSoftKeyboard());
        onView(withId(R.id.insurancesync)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Life Insurance"))).perform(click());
        onView(withId(R.id.insurancesync)).check(matches(withSpinnerText(containsString("Life Insurance"))));

        onView(withId(R.id.paymentsync)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Cash Only"))).perform(click());
        onView(withId(R.id.paymentsync)).check(matches(withSpinnerText(containsString("Cash Only"))));

        onView(withId(R.id.listViewServices)).perform(scrollTo());
        onData(anything()).inAdapterView(withId(R.id.listViewServices)).atPosition(2).perform(longClick());
        onView(withId(R.id.listViewServicesAdded)).perform(scrollTo());
        onData(anything()).inAdapterView(withId(R.id.listViewServicesAdded)).atPosition(0).perform(longClick());


        onView(withId(R.id.NstartHM)).perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class)), is("08"))).perform(click());
        onView(withId(R.id.NstartHM)).check(matches(withSpinnerText(containsString("08"))));
        onView(withId(R.id.NstartMM)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.NstartMM)).check(matches(withSpinnerText(containsString("00"))));
        onView(withId(R.id.NendHM)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("22"))).perform(click());
        onView(withId(R.id.NendHM)).check(matches(withSpinnerText(containsString("22"))));
        onView(withId(R.id.NendMM)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.NendMM)).check(matches(withSpinnerText(containsString("00"))));


        onView(withId(R.id.NstartHTU)).perform(scrollTo(),click());
        onData(allOf(is(instanceOf(String.class)), is("08"))).perform(click());
        onView(withId(R.id.NstartHTU)).check(matches(withSpinnerText(containsString("08"))));
        onView(withId(R.id.NstartMTU)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.NstartMTU)).check(matches(withSpinnerText(containsString("00"))));
        onView(withId(R.id.NendHTU)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("22"))).perform(click());
        onView(withId(R.id.NendHTU)).check(matches(withSpinnerText(containsString("22"))));
        onView(withId(R.id.NendMTU)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.NendMTU)).check(matches(withSpinnerText(containsString("00"))));



        onView(withId(R.id.NstartHW)).perform(scrollTo(),click());
        onData(allOf(is(instanceOf(String.class)), is("08"))).perform(click());
        onView(withId(R.id.NstartHW)).check(matches(withSpinnerText(containsString("08"))));
        onView(withId(R.id.NstartMW)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.NstartMW)).check(matches(withSpinnerText(containsString("00"))));
        onView(withId(R.id.NendHW)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("22"))).perform(click());
        onView(withId(R.id.NendHW)).check(matches(withSpinnerText(containsString("22"))));
        onView(withId(R.id.NendMW)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.NendMW)).check(matches(withSpinnerText(containsString("00"))));



        onView(withId(R.id.NstartHTH)).perform(scrollTo(),click());
        onData(allOf(is(instanceOf(String.class)), is("08"))).perform(click());
        onView(withId(R.id.NstartHTH)).check(matches(withSpinnerText(containsString("08"))));
        onView(withId(R.id.NstartMTH)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.NstartMTH)).check(matches(withSpinnerText(containsString("00"))));
        onView(withId(R.id.NendHTH)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("22"))).perform(click());
        onView(withId(R.id.NendHTH)).check(matches(withSpinnerText(containsString("22"))));
        onView(withId(R.id.NendMTH)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.NendMTH)).check(matches(withSpinnerText(containsString("00"))));



        onView(withId(R.id.NstartHF)).perform(scrollTo(),click());
        onData(allOf(is(instanceOf(String.class)), is("08"))).perform(click());
        onView(withId(R.id.NstartHF)).check(matches(withSpinnerText(containsString("08"))));
        onView(withId(R.id.NstartMF)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.NstartMF)).check(matches(withSpinnerText(containsString("00"))));
        onView(withId(R.id.NendHF)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("22"))).perform(click());
        onView(withId(R.id.NendHF)).check(matches(withSpinnerText(containsString("22"))));
        onView(withId(R.id.NendMF)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.NendMF)).check(matches(withSpinnerText(containsString("00"))));



        onView(withId(R.id.NstartHSA)).perform(scrollTo(),click());
        onData(allOf(is(instanceOf(String.class)), is("08"))).perform(click());
        onView(withId(R.id.NstartHSA)).check(matches(withSpinnerText(containsString("08"))));
        onView(withId(R.id.NstartMSA)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.NstartMSA)).check(matches(withSpinnerText(containsString("00"))));
        onView(withId(R.id.NendHSA)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("22"))).perform(click());
        onView(withId(R.id.NendHSA)).check(matches(withSpinnerText(containsString("22"))));
        onView(withId(R.id.NendMSA)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.NendMSA)).check(matches(withSpinnerText(containsString("00"))));


        onView(withId(R.id.NstartHSU)).perform(scrollTo(),click());
        onData(allOf(is(instanceOf(String.class)), is("08"))).perform(click());
        onView(withId(R.id.NstartHSU)).check(matches(withSpinnerText(containsString("08"))));
        onView(withId(R.id.NstartMSU)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.NstartMSU)).check(matches(withSpinnerText(containsString("00"))));
        onView(withId(R.id.NendHSU)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("22"))).perform(click());
        onView(withId(R.id.NendHSU)).check(matches(withSpinnerText(containsString("22"))));
        onView(withId(R.id.NendMSU)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("00"))).perform(click());
        onView(withId(R.id.NendMSU)).check(matches(withSpinnerText(containsString("00"))));

        onView(ViewMatchers.withId(R.id.drawable_layout2)).perform(DrawerActions.open());
        onView(withId(R.id.navigationView2)).perform(NavigationViewActions.navigateTo(R.id.save));
        onView(ViewMatchers.withId(R.id.drawable_layout2)).perform(DrawerActions.close());


        onView(withText("Clinic updated")).inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }


}
