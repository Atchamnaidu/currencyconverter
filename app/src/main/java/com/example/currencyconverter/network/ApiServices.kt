package com.example.currencyconverter.network

import com.example.currencyconverter.model.ExchangeRates
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {
    @GET("latest.json")
    suspend fun getExchangeRates(): Response<ExchangeRates>
}