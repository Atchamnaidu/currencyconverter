package com.example.currencyconverter.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.currencyconverter.model.ExchangeRates

@Database(entities = [ExchangeRates::class], version = 1)
@TypeConverters(MapTypeConverter::class)
abstract class ExchangeRatesDatabase : RoomDatabase() {
    abstract fun getExchangeRatesDao(): ExchangeRatesDao
}