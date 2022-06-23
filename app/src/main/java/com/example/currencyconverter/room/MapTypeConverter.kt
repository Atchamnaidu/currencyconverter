package com.example.currencyconverter.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.math.BigDecimal

object MapTypeConverter {
    @TypeConverter
    @JvmStatic
    fun stringToMap(value: String): Map<String, BigDecimal> {
        return Gson().fromJson(value, object : TypeToken<Map<String, BigDecimal>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun mapToString(value: Map<String, BigDecimal>?): String {
        return if (value == null) "" else Gson().toJson(value)
    }
}