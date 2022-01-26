package com.simararora.bitapp.features.tradingpairs.common.data

import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.model.TickerResponse.TradingPairsResponse
import javax.inject.Inject
import javax.inject.Singleton

interface TradingPairListInMemoryCache {
    fun getCachedTradingPairList(): List<TradingPairsResponse>
    fun updateCache(tradingPairList: List<TradingPairsResponse>)
    fun clearCache()
}

@Singleton
class TradingPairListInMemoryCacheImpl @Inject constructor() : TradingPairListInMemoryCache {

    private val cachedTradingPairs: MutableList<TradingPairsResponse> = mutableListOf()

    override fun getCachedTradingPairList(): List<TradingPairsResponse> {
        synchronized(this) {
            return cachedTradingPairs.toList()
        }
    }

    override fun updateCache(tradingPairList: List<TradingPairsResponse>) {
        synchronized(this) {
            cachedTradingPairs.clear()
            cachedTradingPairs.addAll(tradingPairList)
        }
    }

    override fun clearCache() {
        synchronized(this) {
            cachedTradingPairs.clear()
        }
    }
}
