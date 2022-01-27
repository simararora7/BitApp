package com.simararora.bitapp.di

import com.google.gson.Gson
import com.simararora.bitapp.common.SchedulersProvider
import com.simararora.bitapp.features.tradingpairs.common.data.TradingPairListInMemoryCache
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface AppDeps {
    fun schedulersProvider(): SchedulersProvider
    fun retrofit(): Retrofit
    fun gson(): Gson
    fun tradingPairListInMemoryCache(): TradingPairListInMemoryCache
}
