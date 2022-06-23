package com.example.currencyconverter.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.model.Currency
import com.example.currencyconverter.model.ExchangeRates
import com.example.currencyconverter.network.Result
import com.example.currencyconverter.repository.ExchangeRatesRepository
import kotlinx.coroutines.*
import java.math.BigDecimal
import javax.inject.Inject

class ExchangeRatesViewModel @Inject constructor(private var repository: ExchangeRatesRepository) :
    ViewModel() {

    private var job: Job? = null
    internal var enteredAmount: BigDecimal = BigDecimal(1)
    private var fraction: BigDecimal = BigDecimal(1)
    internal var prevCurrency: String? = null
    internal var baseCurrency: String? = null
    var amount: MutableLiveData<String> = MutableLiveData()
    private val _exchangeRates = MutableLiveData<Result<ExchangeRates>>()
    internal val exchangeRates: LiveData<Result<ExchangeRates>>
        get() = _exchangeRates

    private val _adapterModel = MutableLiveData<ArrayList<Currency>>()
    internal val adapterModel: LiveData<ArrayList<Currency>>
        get() = _adapterModel

    internal var currencyList: List<String> = ArrayList()
    internal var currencyValues: Map<String, BigDecimal?> = HashMap()

    // For free account, api returning exchange rates with USD as base currency
    // calculate fraction as baseCurr/selectedCurr when a new currency selected
    // multiply currencies with fraction and enter amount to get new exchange rates as per selected currency
    internal fun calculateFraction(selectedCur: String) {
        val baseCurVal = currencyValues[baseCurrency]
        val selectedCurVal = currencyValues[selectedCur]
        if (baseCurVal != null && selectedCurVal != null) {
            fraction = baseCurVal.divide(selectedCurVal, 10, BigDecimal.ROUND_HALF_EVEN)
        }
        updateAdapterData()
    }

    // Prepares currencies with exchange rates.
    // For free account, api returning exchange rates with USD as base currency
    // fraction: baseCurrency/selectedCurrency when a new currency selected
    // multiply currencies with fraction and enter amount to get new exchange rates as per selected currency
    internal fun updateAdapterData() {
        val adapterModel: ArrayList<Currency> = ArrayList()
        for ((key, value) in currencyValues) {
            val amount = value?.let {
                it * fraction * enteredAmount
            }

            adapterModel.add(
                Currency(
                    key,
                    amount?.setScale(4, BigDecimal.ROUND_HALF_EVEN)
                        ?.stripTrailingZeros()?.toPlainString()
                )
            )
        }
        _adapterModel.value = adapterModel
    }


    // Invokes Exchange rate api periodically( every 30 min)
    internal fun getExchangeRates() {
        stopJob()
        job = viewModelScope.launch(Dispatchers.IO) {
            while (true) {
                _exchangeRates.postValue(Result.Loading())
                val response = repository.getExchangeRates()
                _exchangeRates.postValue(response)

                withContext(Dispatchers.Main) {
                    baseCurrency = response.data?.base
                    currencyList = response.data?.rates?.keys?.toList() ?: ArrayList()
                    currencyValues = response.data?.rates ?: HashMap()
                }

                delay(1_800_000) // 30 min delay
            }
        }
    }

    init {
        getExchangeRates()
    }

    internal fun stopJob() {
        job?.cancel()
        job = null
    }

    override fun onCleared() {
        stopJob()
        super.onCleared()
    }
}