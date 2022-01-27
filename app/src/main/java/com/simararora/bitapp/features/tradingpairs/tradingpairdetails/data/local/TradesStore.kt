package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.local

import com.simararora.bitapp.common.SizeLimitLinkedHashMap
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.TradeResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.di.TradingPairDetailScope
import javax.inject.Inject

@TradingPairDetailScope
class TradesStore @Inject constructor() {
    private val tradesMap = SizeLimitLinkedHashMap<Long, TradeResponse>(10)

    fun addTrade(tradeResponse: TradeResponse) {
        synchronized(this) {
            tradesMap[tradeResponse.id] = tradeResponse
        }
    }

    fun getAllTrades(): List<TradeResponse> {
        synchronized(this) {
            return tradesMap.values.toList()
        }
    }

    fun clear() {
        synchronized(this) {
            tradesMap.clear()
        }
    }
}
