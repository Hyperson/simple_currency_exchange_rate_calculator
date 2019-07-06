package com.playground.kotlinSample.model

import com.google.gson.annotations.SerializedName
import java.util.concurrent.ConcurrentHashMap

data class CurrencyItem (
    @SerializedName("base") var base: String,
    @SerializedName("date") var date: String,
    @SerializedName("rates") var rates: ConcurrentHashMap<String, Double>)