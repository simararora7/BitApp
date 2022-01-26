package com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.repository

import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.api.TradingPairListApi
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.mapper.TradingPairResponseMapper
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.model.TickerResponse.TradingPairsResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.di.TradingPairListScope
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model.TradingPair
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.repository.TradingPairListRepository
import io.reactivex.Single
import javax.inject.Inject

@TradingPairListScope
class TradingPairListRepositoryImpl @Inject constructor(
    private val tradingPairListApi: TradingPairListApi,
    private val tradingPairResponseMapper: TradingPairResponseMapper
) : TradingPairListRepository {
    override fun getTradingPairList(symbols: String): Single<List<TradingPair>> {
        return tradingPairListApi.getTradingPairs(symbols).map { items ->
            val result = mutableListOf<TradingPairsResponse>()
            items.forEach { ticker ->
                if (ticker is TradingPairsResponse) {
                    result.add(ticker)
                }
            }
            result
        }.map(tradingPairResponseMapper::mapList)
    }
}
