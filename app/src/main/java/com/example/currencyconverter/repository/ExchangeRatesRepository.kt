package com.example.currencyconverter.repository

import com.example.currencyconverter.model.ExchangeRates
import com.example.currencyconverter.network.Result

interface ExchangeRatesRepository {
    suspend fun getExchangeRates(): Result<ExchangeRates>
}