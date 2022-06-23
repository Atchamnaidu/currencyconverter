package com.example.currencyconverter.utils

class Constants {
    companion object {
        const val BASE_URL: String = "https://openexchangerates.org/api/"
        const val APP_ID: String = "app_id"

        //const val APP_ID_VAL: String = "2e1fed859a7b422ab9595165aad1289c"  // Naidu acct
        const val APP_ID_VAL: String = "ba8e3c2f3b124f4d9cc44b0a9eaff215"     // Suresh acct
        const val CON_TIMEOUT: Long = 60
        const val READ_TIMEOUT: Long = 60

        const val ERROR = "Something went wrong!!"
    }
}