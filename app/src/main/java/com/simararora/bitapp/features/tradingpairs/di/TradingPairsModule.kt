package com.simararora.bitapp.features.tradingpairs.di

import com.simararora.bitapp.features.tradingpairs.data.api.TradingPairsApi
import com.simararora.bitapp.features.tradingpairs.data.repository.TradingPairsRepositoryImpl
import com.simararora.bitapp.features.tradingpairs.domain.repository.TradingPairsRepository
import com.simararora.bitapp.features.tradingpairs.domain.usecase.GetTradingPairs
import com.simararora.bitapp.features.tradingpairs.domain.usecase.GetTradingPairsImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class TradingPairsModule {

    @Provides
    @TradingPairsScope
    fun provideGetTradingPairs(
        getTradingPairsImpl: GetTradingPairsImpl
    ): GetTradingPairs = getTradingPairsImpl

    @Provides
    @TradingPairsScope
    fun provideTradingPairsRepository(
        tradingPairsRepositoryImpl: TradingPairsRepositoryImpl
    ): TradingPairsRepository = tradingPairsRepositoryImpl

    @Provides
    @TradingPairsScope
    fun provideTradingPairsApi(
        retrofit: Retrofit
    ): TradingPairsApi = TradingPairsApi.build(retrofit)
}
