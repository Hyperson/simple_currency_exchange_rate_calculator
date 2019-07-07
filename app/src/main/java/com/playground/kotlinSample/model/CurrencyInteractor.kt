package com.playground.kotlinSample.model

import com.playground.kotlinSample.remote.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * This class retrieve currency data and notify presenter when successful or failed
 */
class CurrencyInteractor {

    interface OnFinishedListener {
        /**
         * Callback called when the result is successful
         */
        fun onResultSuccess(currencyItem: CurrencyItem)

        /**
         * Callback called when the result is failed
         */
        fun onResultFail(strError: Throwable)
    }

    private lateinit var onFinishedListener: OnFinishedListener

    /**
     * Request the currency data from the backend
     */
    fun requestCurrencyData(currencyIsoCode: String, currencyOnFinishedListener: OnFinishedListener): Disposable {
        this.onFinishedListener = currencyOnFinishedListener
        val apiService by lazy {
            Api.create()
        }

        return apiService.getLatestCurrency(currencyIsoCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse, this::handleError)
    }

    private fun handleResponse(currencyItem: CurrencyItem) {
        onFinishedListener.onResultSuccess(currencyItem)
    }

    private fun handleError(error: Throwable) {
        onFinishedListener.onResultFail(error)
    }
}