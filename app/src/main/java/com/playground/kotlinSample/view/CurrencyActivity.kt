package com.playground.kotlinSample.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.playground.kotlinSample.model.CurrencyInteractor
import com.playground.kotlinSample.model.CurrencyItem
import com.playground.kotlinSample.presenter.CurrencyPresenter
import kotlinx.android.synthetic.main.activity_currency.*


class MainActivity : AppCompatActivity(), CurrencyView {

    private lateinit var currencyPresenter: CurrencyPresenter
    private lateinit var baseCurrency: String
    private lateinit var currencyAdapter: CurrencyAdapter
    private var currencyQuantity: Double = 1.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.playground.kotlinSample.R.layout.activity_currency)
        currencyPresenter = CurrencyPresenter(this, CurrencyInteractor())
        listeners()
    }

    private fun listeners() {
        quantity.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == "") {
                    quantity.setText("1")
                    quantity.setSelection(quantity.text!!.length)
                    currencyAdapter.updateCurrencyAmount(1.0)
                    return
                }

                if (s != null) {
                    currencyQuantity = s.toString().toDouble()
                    currencyAdapter.updateCurrencyAmount(currencyQuantity)
                }
            }
        })
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun setCurrencyData(currencyItem: CurrencyItem) {
        currencyPresenter.removeDuplicateCurrency(baseCurrency, currencyItem)

        date.text = currencyItem.date
        currencyAdapter = CurrencyAdapter(this, currencyItem) {
            quantity.setText("1")
            quantity.setSelection(quantity.text!!.length)
            baseCurrency = it.code
            countryflag.setImageResource(it.flag)
            countryname.text = it.name
            currencyPresenter.getCurrencyList(baseCurrency)
        }

        recyclerview.adapter = currencyAdapter
        quantity.isEnabled = true
    }

    override fun getDataFailed(strError: String) {
        Toast.makeText(this, strError, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        val defaultCurrency = currencyPresenter.initLocalCurrency()
        countryflag.setImageResource(defaultCurrency.flag)
        countryname.text = defaultCurrency.name

        baseCurrency = defaultCurrency.code
        currencyPresenter.getCurrencyList(baseCurrency)
    }

    override fun onDestroy() {
        currencyPresenter.onDestroy()
        super.onDestroy()
    }
}
