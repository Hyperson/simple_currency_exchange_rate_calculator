package com.playground.kotlinSample.presenter

import com.mynameismidori.currencypicker.ExtendedCurrency
import com.playground.kotlinSample.getExtendedCurrency
import com.playground.kotlinSample.model.CurrencyInteractor
import com.playground.kotlinSample.model.CurrencyItem
import com.playground.kotlinSample.view.CurrencyView
import io.reactivex.disposables.CompositeDisposable

class CurrencyPresenter(
    private var currencyView: CurrencyView,
    private val currencyInteractor: CurrencyInteractor

) : CurrencyInteractor.OnFinishedListener {

    private val baseCurrencyCode: String = "USD"
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun onResultFail(strError: Throwable) {
        currencyView.hideProgress()
        currencyView.getDataFailed(strError.message!!)
    }

    override fun onResultSuccess(currencyItem: CurrencyItem) {
        currencyView.hideProgress()
        currencyView.setCurrencyData(currencyItem)
    }

    /**
     * Retrieve currency from API
     */
    fun getCurrencyList(currencyIsoCode: String) {
        currencyView.showProgress()
        compositeDisposable.add(currencyInteractor.requestCurrencyData(currencyIsoCode, this))
    }

    /**
     * Called when activity onDestroy is called
     */
    fun onDestroy() {
        compositeDisposable.dispose()
    }

    /**
     * Initialize the base currency
     */
    fun initLocalCurrency(): ExtendedCurrency {
        return getExtendedCurrency(baseCurrencyCode)
    }

    /**
     * Remove the duplicate currency from the API response
     */
    fun removeDuplicateCurrency(baseCurrency : String, currencyItem: CurrencyItem): CurrencyItem {
        for ((k, v) in currencyItem.rates) {
            if (k == baseCurrency) {
                currencyItem.rates.remove(k)
            }
        }

        return currencyItem
    }
}