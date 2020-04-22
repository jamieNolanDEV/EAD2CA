package com.example.fitnesspal;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddWorkoutViewStats {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addWorkoutViewStats() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.fname),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("tes"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.lname),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.lname),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.lname),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.age),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                2),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("23"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.age), withText("23"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                2),
                        isDisplayed()));
        appCompatEditText6.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.weight),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                3),
                        isDisplayed()));
        appCompatEditText7.perform(click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.weight),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                3),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("130"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.weight), withText("130"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                3),
                        isDisplayed()));
        appCompatEditText9.perform(click());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.height),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                4),
                        isDisplayed()));
        appCompatEditText10.perform(click());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.height),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                4),
                        isDisplayed()));
        appCompatEditText11.perform(replaceText("130"), closeSoftKeyboard());

        ViewInteraction materialRadioButton = onView(
                allOf(withId(R.id.female), withText("Female"),
                        childAtPosition(
                                allOf(withId(R.id.gender),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                5)),
                                1),
                        isDisplayed()));
        materialRadioButton.perform(click());

        ViewInteraction materialRadioButton2 = onView(
                allOf(withId(R.id.male), withText("Male"),
                        childAtPosition(
                                allOf(withId(R.id.gender),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                5)),
                                0),
                        isDisplayed()));
        materialRadioButton2.perform(click());

        ViewInteraction materialRadioButton3 = onView(
                allOf(withId(R.id.male), withText("Male"),
                        childAtPosition(
                                allOf(withId(R.id.gender),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                5)),
                                0),
                        isDisplayed()));
        materialRadioButton3.perform(click());

        ViewInteraction materialRadioButton4 = onView(
                allOf(withId(R.id.male), withText("Male"),
                        childAtPosition(
                                allOf(withId(R.id.gender),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                5)),
                                0),
                        isDisplayed()));
        materialRadioButton4.perform(click());

        ViewInteraction materialRadioButton5 = onView(
                allOf(withId(R.id.male), withText("Male"),
                        childAtPosition(
                                allOf(withId(R.id.gender),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                5)),
                                0),
                        isDisplayed()));
        materialRadioButton5.perform(click());

        ViewInteraction materialRadioButton6 = onView(
                allOf(withId(R.id.male), withText("Male"),
                        childAtPosition(
                                allOf(withId(R.id.gender),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                5)),
                                0),
                        isDisplayed()));
        materialRadioButton6.perform(click());

        ViewInteraction materialRadioButton7 = onView(
                allOf(withId(R.id.male), withText("Male"),
                        childAtPosition(
                                allOf(withId(R.id.gender),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                5)),
                                0),
                        isDisplayed()));
        materialRadioButton7.perform(click());

        ViewInteraction materialRadioButton8 = onView(
                allOf(withId(R.id.male), withText("Male"),
                        childAtPosition(
                                allOf(withId(R.id.gender),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                5)),
                                0),
                        isDisplayed()));
        materialRadioButton8.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.add), withText("Begin"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                6),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.addWorkout),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.Day),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText12.perform(click());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.Day),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText13.perform(replaceText("20"), closeSoftKeyboard());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.Month),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText14.perform(replaceText("04"), closeSoftKeyboard());

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.Year),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText15.perform(replaceText("2020"), closeSoftKeyboard());

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.Year), withText("2020"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText16.perform(click());

        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.wDuration),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatEditText17.perform(replaceText("30"), closeSoftKeyboard());

        ViewInteraction appCompatEditText18 = onView(
                allOf(withId(R.id.calBurned),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                2),
                        isDisplayed()));
        appCompatEditText18.perform(replaceText("30"), closeSoftKeyboard());

        ViewInteraction appCompatEditText19 = onView(
                allOf(withId(R.id.wDescription),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                3),
                        isDisplayed()));
        appCompatEditText19.perform(replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatEditText20 = onView(
                allOf(withId(R.id.wDescription), withText("test"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                3),
                        isDisplayed()));
        appCompatEditText20.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.add), withText("Add Workout"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                4),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.addWorkout),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        pressBack();

        pressBack();

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.workout),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView3.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.workoutlist),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                2)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.back), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                8),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.back), withText("Back"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatImageView4 = onView(
                allOf(withId(R.id.stats),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.cardview.widget.CardView")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView4.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.lWorkout), withText("30.0"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class),
                                        1),
                                9),
                        isDisplayed()));
        textView.check(matches(withText("30.0")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
