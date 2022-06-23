package com.example.currencyconverter.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.currencyconverter.R

@BindingAdapter("currencyvalue")
fun TextView.addCurrency(currency: String?) {
    text = currency ?: this.context.getString(R.string.no_currency)
}