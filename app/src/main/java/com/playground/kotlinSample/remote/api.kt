package com.playground.kotlinSample.remote

import com.playground.kotlinSample.model.CurrencyItem
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    companion object {
        private const val BASE_URL = "https://api.exchangeratesapi.io/"

        fun create(): Api {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(
                Api::class.java
            )
        }
    }

    @GET("latest")
    fun getLatestCurrency(@Query("base") baseCurrency : String): Observable<CurrencyItem>
}