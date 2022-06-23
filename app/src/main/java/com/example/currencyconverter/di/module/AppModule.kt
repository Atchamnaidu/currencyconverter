package com.example.currencyconverter.di.module

import android.content.Context
import com.example.currencyconverter.CurrencyConverterApplication
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun providesContext(application: CurrencyConverterApplication): Context
}