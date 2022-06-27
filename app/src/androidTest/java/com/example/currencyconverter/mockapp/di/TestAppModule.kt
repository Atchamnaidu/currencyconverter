package com.example.currencyconverter.mockapp.di

import android.content.Context
import com.example.currencyconverter.mockapp.TestCurrencyConverterApplication
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class TestAppModule {

    @Singleton
    @Binds
    abstract fun providesContext(application: TestCurrencyConverterApplication): Context
}