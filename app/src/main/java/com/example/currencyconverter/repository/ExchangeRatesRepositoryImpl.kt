package com.example.currencyconverter.repository

import com.example.currencyconverter.model.ExchangeRates
import com.example.currencyconverter.network.ApiServices
import com.example.currencyconverter.network.NoConnectivityException
import com.example.currencyconverter.network.Result
import com.example.currencyconverter.room.ExchangeRatesDatabase
import com.example.currencyconverter.utils.Constants.Companion.ERROR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExchangeRatesRepositoryImpl @Inject constructor(
    private var apiServices: ApiServices,
    private var roomDatabase: ExchangeRatesDatabase
) :
    ExchangeRatesRepository {

    override suspend fun getExchangeRates(): Result<ExchangeRates> =
        withContext(Dispatchers.IO) {
            try {
                val response = apiServices.getExchangeRates()
                if (response.isSuccessful && response.body() != null) {
                    roomDatabase.getExchangeRatesDao().insertExchangeRates(response.body()!!)
                    Result.Success(response.body()!!)
                } else {
                    Result.Error(response.errorBody().toString())
                }
            } catch (ex: NoConnectivityException) {
                // get data from room database
                val exRates = roomDatabase.getExchangeRatesDao().getExchangeRates()
                if (exRates != null) {
                    Result.Success(exRates)
                } else {
                    Result.Error(ex.message)
                }
            } catch (ex: Exception) {
                Result.Error(ERROR)
            }
        }
}