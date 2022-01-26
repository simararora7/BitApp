package com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.usecase

import com.simararora.bitapp.common.SingleUseCase
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.di.TradingPairListScope
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model.TradingPair
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.repository.TradingPairListRepository
import io.reactivex.Single
import javax.inject.Inject

interface GetTradingPairs : SingleUseCase<String, List<TradingPair>>

@TradingPairListScope
class GetTradingPairListImpl @Inject constructor(
    private val repository: TradingPairListRepository
) : GetTradingPairs {

    override fun execute(param: String): Single<List<TradingPair>> {
        return repository.getTradingPairList(param)
    }
}
