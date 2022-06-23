package com.example.currencyconverter

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.currencyconverter.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_amount_edit_text_is_visible() {

        onView(withId(R.id.etAmount)).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun test_currencies_drop_down_is_visible() {

        onView(withId(R.id.tilSpinner)).check(
            matches(isDisplayed())
        )
        onView(withId(R.id.spinner)).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun test_all_currencies_recylerview_is_visible() {
        onView(withId(R.id.rvCurrencies)).check(
            matches(isDisplayed())
        )
    }
}
