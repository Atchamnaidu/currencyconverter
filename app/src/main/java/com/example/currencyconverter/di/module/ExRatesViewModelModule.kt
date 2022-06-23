package com.example.currencyconverter.di.module

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.di.ActivityScope
import com.example.currencyconverter.viewmodel.ExchangeRatesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ExRatesViewModelModule {

    @ActivityScope
    @Binds
    @IntoMap
    @ClassKey(ExchangeRatesViewModel::class)
    abstract fun providesExchangeRatesViewModel(exchangeRatesViewModel: ExchangeRatesViewModel): ViewModel
}