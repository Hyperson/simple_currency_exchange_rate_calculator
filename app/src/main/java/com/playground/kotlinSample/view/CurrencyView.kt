package com.playground.kotlinSample.view

import com.playground.kotlinSample.model.CurrencyItem

/**
 * View contract for CurrencyPresenter / CurrencyActivity
 */
interface CurrencyView {
    fun showProgress()
    fun hideProgress()
    fun setCurrencyData(currencyItem: CurrencyItem)
    fun getDataFailed(strError: String)
}