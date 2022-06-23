package com.example.currencyconverter.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(private var viewModelMap: Map<Class<*>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        viewModelMap[modelClass]?.get()?.let { viewModel ->
            return viewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel type")
    }
}