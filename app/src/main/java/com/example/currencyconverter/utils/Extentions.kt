package com.example.currencyconverter

import android.app.Activity
import android.view.inputmethod.InputMethodManager

fun Activity.hideSoftKeyboard() {
    val inputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocus = this.currentFocus
    val windowToken = this.window?.decorView?.rootView?.windowToken
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    inputMethodManager.hideSoftInputFromWindow(
        windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
    if (currentFocus != null) {
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}