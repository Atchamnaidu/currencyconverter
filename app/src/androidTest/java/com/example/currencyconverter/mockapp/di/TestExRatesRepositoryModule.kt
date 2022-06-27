package com.example.currencyconverter.mockapp.di

import com.example.currencyconverter.FakeExchangeRatesRepository
import com.example.currencyconverter.StubData
import com.example.currencyconverter.di.ActivityScope
import com.example.currencyconverter.repository.ExchangeRatesRepository
import dagger.Module
import dagger.Provides

@Module
class TestExRatesRepositoryModule {

    @ActivityScope
    @Provides
    fun provideExRatesRepository(): ExchangeRatesRepository {
        return FakeExchangeRatesRepository(StubData.data)
    }
}