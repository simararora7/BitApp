package com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.repository

import com.simararora.bitapp.features.tradingpairs.common.data.TradingPairListInMemoryCache
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.api.TradingPairListApi
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.mapper.TradingPairResponseMapper
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.model.TickerResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.model.TickerResponse.TradingPairsResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.di.TradingPairListScope
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model.TradingPair
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.repository.TradingPairListRepository
import io.reactivex.Single
import javax.inject.Inject

@TradingPairListScope
class TradingPairListRepositoryImpl @Inject constructor(
    private val tradingPairListApi: TradingPairListApi,
    private val tradingPairResponseMapper: TradingPairResponseMapper,
    private val tradingPairListInMemoryCache: TradingPairListInMemoryCache
) : TradingPairListRepository {

    override fun getTradingPairList(symbols: String): Single<List<TradingPair>> {
        return tradingPairListApi.getTradingPairs(symbols)
            .map(::filterTradingPairs)
            .doOnSuccess(tradingPairListInMemoryCache::updateCache)
            .map(tradingPairResponseMapper::mapList)
    }

    private fun filterTradingPairs(tickers: List<TickerResponse>): List<TradingPairsResponse> {
        val tradingPairs = mutableListOf<TradingPairsResponse>()
        tickers.forEach { ticker ->
            if (ticker is TradingPairsResponse) {
                tradingPairs.add(ticker)
            }
        }
        return tradingPairs
    }
}
