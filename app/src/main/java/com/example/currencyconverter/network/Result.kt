package com.example.currencyconverter.network

sealed class Result<T>(
    val data: T? = null,
    val errorMsg: String? = null
) {
    class Loading<T> : Result<T>()
    class Success<T>(data: T? = null) : Result<T>(data = data)
    class Error<T>(errorMsg: String) : Result<T>(errorMsg = errorMsg)
}
