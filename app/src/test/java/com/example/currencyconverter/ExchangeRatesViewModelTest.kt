package com.example.currencyconverter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.currencyconverter.base.FakeExchangeRatesRepository
import com.example.currencyconverter.base.MainCoroutineRule
import com.example.currencyconverter.base.getOrAwaitValueTest
import com.example.currencyconverter.model.Currency
import com.example.currencyconverter.model.ExchangeRates
import com.example.currencyconverter.viewmodel.ExchangeRatesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal

@ExperimentalCoroutinesApi
class ExchangeRatesViewModelTest {
    private lateinit var viewModel: ExchangeRatesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    private val rates = mapOf(
        Pair("AED", BigDecimal(10)),
        Pair("IND", BigDecimal(15)),
        Pair("USD", BigDecimal(1))
    )
    private val baseCurrency = "USD"

    @Before
    fun setUp() {
        val data = ExchangeRates(baseCurrency, rates)
        viewModel = ExchangeRatesViewModel(FakeExchangeRatesRepository(data))
    }

    @Test
    fun test_exchange_rates_api_success() = runTest {
        viewModel.getExchangeRates()
        val value = viewModel.exchangeRates.getOrAwaitValueTest {
            advanceUntilIdle()
        }
        Assert.assertTrue(value.data?.rates?.size == 3)
    }

    @Test
    fun test_exchange_rates_currency_list_is_prepared_properly() = runTest {
        viewModel.getExchangeRates()
        viewModel.exchangeRates.getOrAwaitValueTest {
            advanceUntilIdle()
        }
        delay(1000)
        Assert.assertTrue(viewModel.currencyList == arrayListOf("AED", "IND", "USD"))
    }

    @Test
    fun test_exchange_rates_currency_value_list_is_prepared_properly() = runTest {
        viewModel.getExchangeRates()
        viewModel.exchangeRates.getOrAwaitValueTest {
            advanceUntilIdle()
        }
        delay(1000)
        Assert.assertTrue(viewModel.currencyValues == rates)
    }

    @Test
    fun test_exchange_rates_base_currency_is_set_properly() = runTest {
        viewModel.getExchangeRates()
        viewModel.exchangeRates.getOrAwaitValueTest {
            advanceUntilIdle()
        }
        delay(1000)
        Assert.assertTrue(viewModel.baseCurrency == baseCurrency)
    }

    @Test
    fun test_all_currencies_exchange_rates_adapter_data_is_set_properly() = runTest {
        viewModel.getExchangeRates()
        viewModel.exchangeRates.getOrAwaitValueTest {
            advanceUntilIdle()
        }
        delay(1000)
        viewModel.updateAdapterData()
        val value = viewModel.adapterModel.getOrAwaitValueTest {
            advanceUntilIdle()
        }
        Assert.assertTrue(value.size == 3)

        Assert.assertTrue(
            value == listOf(
                Currency("AED", "10"),
                Currency("IND", "15"),
                Currency("USD", "1")
            )
        )
    }

    @Test
    fun test_base_currency_and_selected_new_currency_fraction_is_calculated_properly() = runTest {
        viewModel.getExchangeRates()
        viewModel.exchangeRates.getOrAwaitValueTest {
            advanceUntilIdle()
        }
        delay(1000)
        viewModel.calculateFraction("IND")
        val fraction =
            viewModel.javaClass.getDeclaredField("fraction")
        fraction.isAccessible = true

        val fractionValue: BigDecimal = fraction.get(viewModel) as BigDecimal
        val expected: BigDecimal? =
            rates["USD"]?.divide(rates["IND"], 10, BigDecimal.ROUND_HALF_EVEN)
        Assert.assertTrue(fractionValue == expected)
    }


}