package com.example.medical;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4ClassRunner.class)
public class IndicatorsActivityTest {


    @Test
    public void testIndicatorsActivityView() {
        ActivityScenario.launch(IndicatorsActivity.class);
        onView(withId(R.id.activity_indicators)).check(matches(isDisplayed()));
    }

// Optional:
//    @Test
//    public void testLoginActivityView() {
//        ActivityScenario.launch(IndicatorsActivity.class);
//        onView(withId(R.id.activity_indicators)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
//    }

    @Test
    public void testAge() {
        ActivityScenario.launch(IndicatorsActivity.class);
        onView(withId(R.id.age)).perform(typeText("0"), closeSoftKeyboard());
        onView(withId(R.id.diagnoseB)).perform(click());
    }

    @Test
    public void testHighWhiteCells() {
        ActivityScenario.launch(IndicatorsActivity.class);
        onView(withId(R.id.patientId)).perform(typeText("312219322"), closeSoftKeyboard());
        onView(withId(R.id.age)).perform(typeText("20"), closeSoftKeyboard());
        onView(withId(R.id.whiteCells)).perform(typeText("20000"), closeSoftKeyboard());
        onView(withId(R.id.diagnoseB)).perform(click());
    }

    @Test
    public void testUnderZeroIron() {
        ActivityScenario.launch(IndicatorsActivity.class);
        onView(withId(R.id.patientId)).perform(typeText("312219322"), closeSoftKeyboard());
        onView(withId(R.id.age)).perform(typeText("20"), closeSoftKeyboard());
        onView(withId(R.id.whiteCells)).perform(typeText("20000"), closeSoftKeyboard());
        onView(withId(R.id.ironL)).perform(typeText("-20"), closeSoftKeyboard());
        onView(withId(R.id.diagnoseB)).perform(click());
    }

    @Test
    public void testStringIntoIron() {
        ActivityScenario.launch(IndicatorsActivity.class);
        onView(withId(R.id.patientId)).perform(typeText("312219322"), closeSoftKeyboard());
        onView(withId(R.id.age)).perform(typeText("20"), closeSoftKeyboard());
        onView(withId(R.id.whiteCells)).perform(typeText("20000"), closeSoftKeyboard());
        onView(withId(R.id.ironL)).perform(typeText("yossi"), closeSoftKeyboard());
        onView(withId(R.id.diagnoseB)).perform(click());
    }

    @Test
    public void test_age_is_null() {
        ActivityScenario.launch(IndicatorsActivity.class);
        onView(withId(R.id.age)).perform(typeText("20"), closeSoftKeyboard());
        onView(withId(R.id.age)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.diagnoseB)).perform(click());
    }
}