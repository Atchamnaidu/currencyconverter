package com.example.currencyconverter

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.example.currencyconverter.StubData.baseCurrency
import com.example.currencyconverter.StubData.data
import com.example.currencyconverter.StubData.rates
import com.example.currencyconverter.mockapp.di.DaggerTestComponent
import com.example.currencyconverter.mockapp.di.TestComponent
import com.example.currencyconverter.view.MainActivity
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal


@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    private lateinit var testAppComponent: TestComponent

    @Before
    fun setUp() {
        val app =
            getInstrumentation().targetContext.applicationContext as CurrencyConverterApplication
        testAppComponent = DaggerTestComponent.builder()
            .build()
        app.appComponent = testAppComponent
    }

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

    @Test
    fun test_that_default_base_currency_is_selected() {
        onView(withId(R.id.spinner))
            .check(matches(withText(baseCurrency)))
    }

    @Test
    fun test_on_click_spinner_currencies_drop_down_opens() {
        onView(withId(R.id.spinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`(data.rates.keys.toList()[0])))
            .inRoot(isPlatformPopup())
            .perform(click())
        onView(withId(R.id.spinner))
            .check(matches(withText(data.rates.keys.toList()[0])))

        onView(withId(R.id.spinner)).perform(click())

        onData(allOf(`is`(instanceOf(String::class.java)), `is`(data.rates.keys.toList()[1])))
            .inRoot(isPlatformPopup())
            .perform(click())
        onView(withId(R.id.spinner))
            .check(matches(withText(data.rates.keys.toList()[1])))
    }

    @Test
    fun test_currencies_recyclerview_showing_data_properly() {
        onView(withId(R.id.rvCurrencies)).check(matches(hasChildCount(3)))
        for ((key, value) in rates) {
            onView(withId(R.id.rvCurrencies)).check(matches(hasDescendant(withText(key))))

            onView(withId(R.id.rvCurrencies)).check(matches(hasDescendant(withText(value.toPlainString()))))
        }

    }


    @Test
    fun test_currencies_recyclerview_data_updated_after_entering_amount() {

        val amount = 10
        onView(withId(R.id.etAmount)).perform(
            typeText("$amount"),
            closeSoftKeyboard()
        )

        for ((key, value) in rates) {
            onView(withId(R.id.rvCurrencies)).check(matches(hasDescendant(withText(containsString(key)))))
            onView(withId(R.id.rvCurrencies)).check(matches(hasDescendant(
                withText(containsString((value * BigDecimal(amount)).toPlainString())))))
        }
    }
}
