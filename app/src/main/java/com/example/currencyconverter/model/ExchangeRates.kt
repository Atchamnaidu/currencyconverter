package com.example.currencyconverter.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

@Entity(tableName = "exchangerates")
data class ExchangeRates(
    @PrimaryKey
    @SerializedName("base")
    val base: String,
    @SerializedName("rates")
    val rates: Map<String, BigDecimal>
)