package com.example.currencyconverter.di.module

import android.content.Context
import androidx.room.Room
import com.example.currencyconverter.room.ExchangeRatesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun providesRoomDatabase(context: Context): ExchangeRatesDatabase {
        return Room.databaseBuilder(
            context,
            ExchangeRatesDatabase::class.java,
            "ExchangeRatesDatabase"
        ).build()
    }
}