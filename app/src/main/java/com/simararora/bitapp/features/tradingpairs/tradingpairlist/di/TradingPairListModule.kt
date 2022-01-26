package com.simararora.bitapp.features.tradingpairs.tradingpairlist.di

import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.api.TradingPairListApi
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.repository.TradingPairListRepositoryImpl
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.repository.TradingPairListRepository
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.usecase.GetTradingPairListImpl
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.usecase.GetTradingPairs
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.TradingPairListNavigator
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.TradingPairListNavigatorImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class TradingPairListModule {

    @Provides
    @TradingPairListScope
    fun provideTradingPairListNavigator(
        tradingPairListNavigatorImpl: TradingPairListNavigatorImpl
    ): TradingPairListNavigator = tradingPairListNavigatorImpl

    @Provides
    @TradingPairListScope
    fun provideGetTradingPairList(
        getTradingPairListImpl: GetTradingPairListImpl
    ): GetTradingPairs = getTradingPairListImpl

    @Provides
    @TradingPairListScope
    fun provideTradingPairListRepository(
        tradingPairsRepositoryImpl: TradingPairListRepositoryImpl
    ): TradingPairListRepository = tradingPairsRepositoryImpl

    @Provides
    @TradingPairListScope
    fun provideTradingPairListApi(
        retrofit: Retrofit
    ): TradingPairListApi = TradingPairListApi.build(retrofit)
}
