package com.playground.kotlinSample

import com.mynameismidori.currencypicker.ExtendedCurrency

/**
 * Retrieve currency code based on currencyIsoCode
 */
fun getExtendedCurrency(currencyIsoCode: String): ExtendedCurrency {
    val currencies = ExtendedCurrency.getAllCurrencies()
    for (currency: ExtendedCurrency in currencies) {
        if (currency.code == currencyIsoCode) {
            return currency
        }
    }
    return ExtendedCurrency()
}




