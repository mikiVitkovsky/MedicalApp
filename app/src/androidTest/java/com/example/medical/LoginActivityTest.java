package com.example.medical;

import androidx.test.core.app.ActivityScenario;
import androidx.test.filters.SmallTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4ClassRunner.class)
public class LoginActivityTest {

    @Test
    public void testEmail() {
        ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.userName)).perform(typeText("yossi6590"), closeSoftKeyboard());
        onView(withId(R.id.userName)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.userName)).perform(typeText("yossi6"), closeSoftKeyboard());
    }

    @Test
    public void testPassword() {
        ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.loginPass)).perform(typeText("123456"), closeSoftKeyboard());
    }

    @Test
    public void testBadLogin() {
        ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.userName)).perform(typeText("yossi6590"), closeSoftKeyboard());
        onView(withId(R.id.loginPass)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.loginB)).perform(click());
    }

    @Test
    public void testGoodLogin() {
        ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.userName)).perform(typeText("yossi6590@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.loginPass)).perform(typeText("!y123456"), closeSoftKeyboard());
        onView(withId(R.id.loginB)).perform(click());
        ActivityScenario.launch(IndicatorsActivity.class);
    }
}