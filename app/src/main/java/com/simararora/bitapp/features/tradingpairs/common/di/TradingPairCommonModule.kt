package com.simararora.bitapp.features.tradingpairs.common.di

import com.simararora.bitapp.features.tradingpairs.common.data.TradingPairListInMemoryCache
import com.simararora.bitapp.features.tradingpairs.common.data.TradingPairListInMemoryCacheImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class TradingPairCommonModule {

    @Binds
    @Singleton
    abstract fun bindTradingPairListInMemoryCache(
        tradingPairListInMemoryCacheImpl: TradingPairListInMemoryCacheImpl
    ): TradingPairListInMemoryCache
}
