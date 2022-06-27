package com.example.currencyconverter.view

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.currencyconverter.R
import com.example.currencyconverter.base.ViewModelFactory
import com.example.currencyconverter.databinding.ActivityMainBinding
import com.example.currencyconverter.hideSoftKeyboard
import com.example.currencyconverter.network.Result
import com.example.currencyconverter.viewmodel.ExchangeRatesViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ExchangeRatesViewModel

    private lateinit var currencyListAdapter: CurrenciesListAdapter
    private lateinit var spinnerAdapter: ArrayAdapter<CharSequence>

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)
        AndroidInjection.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory)[ExchangeRatesViewModel::class.java]
        binding.viewModel = viewModel

        setupSpinner()
        setupRecyclerView()
        initAmountObserver()
        initAdapterDataObserver()
        initExchangeRatesObserver()
    }

    private fun setupSpinner() {
        spinner.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                hideSoftKeyboard()
            }
        }
        spinnerAdapter = ArrayAdapter<CharSequence>(
            this@MainActivity,
            R.layout.spinner_item,
            viewModel.currencyList
        )

        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item)
        spinner.setAdapter(spinnerAdapter)

        spinner.setOnItemClickListener { _, _, position, _ ->
            if (viewModel.prevCurrency.equals(viewModel.currencyList[position])) return@setOnItemClickListener // no action if same Currency selected again
            viewModel.prevCurrency = viewModel.currencyList[position]
            spinner.setText(viewModel.currencyList[position], false)
            viewModel.calculateFraction(viewModel.currencyList[position])
        }
    }

    private fun setupRecyclerView() {
        rvCurrencies.layoutManager = GridLayoutManager(applicationContext, 3)
        currencyListAdapter = CurrenciesListAdapter()
        rvCurrencies.adapter = currencyListAdapter
    }

    private fun initAdapterDataObserver() {
        viewModel.adapterModel.observe(this) { adapterData ->
            currencyListAdapter.setData(adapterData)
        }
    }

    private fun initAmountObserver() {
        viewModel.amount.observe(this) { amount ->
            viewModel.enteredAmount = if (!amount.isNullOrEmpty())
                BigDecimal(amount)
            else
                BigDecimal(1)
            viewModel.updateAdapterData()
        }
    }

    private fun initExchangeRatesObserver() {
        viewModel.exchangeRates.observe(
            this
        ) { response ->
            when (response) {
                is Result.Success -> {
                    progressBar.visibility = View.GONE

                    response.data?.rates?.let {
                        viewModel.currencyList = it.keys.toList()
                        viewModel.currencyValues = it
                        viewModel.baseCurrency = response.data.base
                        updateCurrencyDropdown()
                        viewModel.updateAdapterData()
                    }
                }
                is Result.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, response.errorMsg, Toast.LENGTH_LONG).show()
                }
                is Result.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun updateCurrencyDropdown() {
        spinnerAdapter.clear()
        spinnerAdapter.addAll(viewModel.currencyList)
        spinnerAdapter.notifyDataSetChanged()
        if (viewModel.prevCurrency == null) {  // avoid updating selected currency with base in api (when called again)
            spinner.setText(viewModel.baseCurrency, false)
            viewModel.prevCurrency = viewModel.baseCurrency
        }
    }

    override fun onDestroy() {
        viewModel.stopJob()
        super.onDestroy()
    }
}