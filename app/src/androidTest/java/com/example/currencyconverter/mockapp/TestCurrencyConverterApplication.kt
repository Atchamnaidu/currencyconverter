package com.example.currencyconverter.mockapp

import com.example.currencyconverter.CurrencyConverterApplication
import com.example.currencyconverter.mockapp.di.DaggerTestComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class TestCurrencyConverterApplication @Inject constructor() : CurrencyConverterApplication() {

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerTestComponent.builder().build()
        return appComponent
    }
}