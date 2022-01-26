package com.simararora.bitapp.features.tradingpairs.domain.usecase

import com.simararora.bitapp.common.SingleUseCase
import com.simararora.bitapp.features.tradingpairs.data.model.TickersResponse
import com.simararora.bitapp.features.tradingpairs.data.model.TickersResponse.TradingPairsResponse
import com.simararora.bitapp.features.tradingpairs.di.TradingPairsScope
import com.simararora.bitapp.features.tradingpairs.domain.model.TradingPairs
import com.simararora.bitapp.features.tradingpairs.domain.repository.TradingPairsRepository
import io.reactivex.Single
import javax.inject.Inject

interface GetTradingPairs : SingleUseCase<String, List<TradingPairs>>

@TradingPairsScope
class GetTradingPairsImpl @Inject constructor(
    private val repository: TradingPairsRepository
) : GetTradingPairs {

    override fun execute(param: String): Single<List<TradingPairs>> {
        return repository.getTradingPairs(param)
    }
}
