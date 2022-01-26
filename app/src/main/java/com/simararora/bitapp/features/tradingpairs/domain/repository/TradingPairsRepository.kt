package com.simararora.bitapp.features.tradingpairs.domain.repository

import com.simararora.bitapp.features.tradingpairs.data.model.TickersResponse
import com.simararora.bitapp.features.tradingpairs.domain.model.TradingPairs
import io.reactivex.Single

interface TradingPairsRepository {
    fun getTradingPairs(symbols: String): Single<List<TradingPairs>>
}
