package com.example.currencyconverter.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconverter.model.ExchangeRates

@Dao
interface ExchangeRatesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExchangeRates(exchangeRates: ExchangeRates)

    @Query("SELECT * FROM exchangerates")
    suspend fun getExchangeRates(): ExchangeRates?
}