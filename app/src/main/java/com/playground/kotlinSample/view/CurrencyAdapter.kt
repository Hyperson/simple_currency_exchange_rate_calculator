package com.playground.kotlinSample.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mynameismidori.currencypicker.ExtendedCurrency
import com.playground.kotlinSample.R
import com.playground.kotlinSample.model.CurrencyItem
import kotlinx.android.synthetic.main.currency_list_item.view.*
import java.util.concurrent.ConcurrentHashMap

class CurrencyAdapter(
    private val context: Context,
    private var currencyItem: CurrencyItem,
    private val listener: (ExtendedCurrency) -> Unit
) : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    private var cQuantity: Double = 1.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.currency_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return currencyItem.rates.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val keySet = currencyItem.rates.keys.toTypedArray()
        val currencyIsoCode = keySet[position]

        val extendedCurrency: ExtendedCurrency = getExtendedCurrency(currencyIsoCode)

        viewHolder.country?.setImageResource(extendedCurrency.flag)
        viewHolder.currency?.text = context.resources.getString(
            R.string.formatted_currency_name,
            extendedCurrency.name, extendedCurrency.code
        )

        viewHolder.value?.text = context.resources.getString(
            R.string.formatted_currency_value,
            String.format("%.3f", currencyItem.rates[currencyIsoCode]?.times(cQuantity)), extendedCurrency.symbol
        )

        viewHolder.itemView.setOnClickListener { listener(extendedCurrency) }
    }

    fun updateCurrencyAmount(currencyQuantity: Double) {
        cQuantity = currencyQuantity
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val country = view.country
        val currency = view.currency
        val value = view.value
    }

    //TODO Remove this and implement a proper solution
    private fun getExtendedCurrency(currencyIsoCode: String): ExtendedCurrency {
        val currencies = ExtendedCurrency.getAllCurrencies()
        for (currency: ExtendedCurrency in currencies) {
            if (currency.code == currencyIsoCode) {
                return currency
            }
        }
        return ExtendedCurrency()
    }
}