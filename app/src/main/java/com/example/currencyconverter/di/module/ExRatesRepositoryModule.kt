package com.example.currencyconverter.di.module

import com.example.currencyconverter.di.ActivityScope
import com.example.currencyconverter.network.ApiServices
import com.example.currencyconverter.repository.ExchangeRatesRepository
import com.example.currencyconverter.repository.ExchangeRatesRepositoryImpl
import com.example.currencyconverter.room.ExchangeRatesDatabase
import dagger.Module
import dagger.Provides

@Module
class ExRatesRepositoryModule {

    @ActivityScope
    @Provides
    fun provideExRatesRepository(
        apiServices: ApiServices,
        room: ExchangeRatesDatabase
    ): ExchangeRatesRepository =
        ExchangeRatesRepositoryImpl(apiServices, room)
}