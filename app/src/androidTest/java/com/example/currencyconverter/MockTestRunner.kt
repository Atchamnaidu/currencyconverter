package com.example.currencyconverter

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.example.currencyconverter.mockapp.TestCurrencyConverterApplication

class MockTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(
            cl,
            TestCurrencyConverterApplication::class.java.name,
            context
        )
    }
}