package com.example.currencyconverter.base

import com.example.currencyconverter.model.ExchangeRates
import com.example.currencyconverter.network.Result
import com.example.currencyconverter.repository.ExchangeRatesRepository
import java.math.BigDecimal

class FakeExchangeRatesRepository(private val data: ExchangeRates) : ExchangeRatesRepository {
    override suspend fun getExchangeRates(): Result<ExchangeRates> =
        Result.Success(data)
}
