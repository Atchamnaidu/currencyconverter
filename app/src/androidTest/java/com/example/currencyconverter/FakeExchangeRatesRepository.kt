package com.example.currencyconverter

import com.example.currencyconverter.model.ExchangeRates
import com.example.currencyconverter.network.Result
import com.example.currencyconverter.repository.ExchangeRatesRepository
import javax.inject.Inject

class FakeExchangeRatesRepository @Inject constructor(private val data: ExchangeRates) :
    ExchangeRatesRepository {
    override suspend fun getExchangeRates(): Result<ExchangeRates> =
        Result.Success(data)
}
