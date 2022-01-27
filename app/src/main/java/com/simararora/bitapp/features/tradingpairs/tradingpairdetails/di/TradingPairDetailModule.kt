package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.di

import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.repository.TradingPairDetailsRepositoryImpl
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.repository.TradingPairDetailsRepository
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.usecase.TradingPairDetailUseCase
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.usecase.TradingPairDetailUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
abstract class TradingPairDetailModule {

    @Binds
    @TradingPairDetailScope
    abstract fun bindTradingPairDetailUseCase(
        TradingPairDetailUseCaseImpl: TradingPairDetailUseCaseImpl
    ): TradingPairDetailUseCase

    @Binds
    @TradingPairDetailScope
    abstract fun bindTradingPairDetailsRepository(
        tradingPairDetailsRepositoryImpl: TradingPairDetailsRepositoryImpl
    ): TradingPairDetailsRepository
}
