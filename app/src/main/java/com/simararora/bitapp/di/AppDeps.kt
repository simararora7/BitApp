package com.simararora.bitapp.di

import com.simararora.bitapp.common.SchedulersProvider
import com.simararora.bitapp.features.tradingpairs.common.data.TradingPairListInMemoryCache
import retrofit2.Retrofit

interface AppDeps {
    fun schedulersProvider(): SchedulersProvider
    fun retrofit(): Retrofit
    fun tradingPairListInMemoryCache(): TradingPairListInMemoryCache
}
