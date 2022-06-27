package com.example.currencyconverter

import com.example.currencyconverter.model.ExchangeRates
import java.math.BigDecimal

object StubData {
    val rates = mapOf(
        Pair("AED", BigDecimal(10)),
        Pair("IND", BigDecimal(15)),
        Pair("USD", BigDecimal(1))
    )
    val baseCurrency = "USD"

    val data = ExchangeRates(baseCurrency, rates)
}