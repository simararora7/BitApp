package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.repository

import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.Trade
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.TradingPairDetail
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model.TradingPair
import io.reactivex.Observable
import io.reactivex.Single

interface TradingPairDetailsRepository {
    fun getTradingPairDetails(symbol: String): Observable<TradingPairDetail>
    fun getTopTrades(symbol: String): Observable<List<Trade>>
    fun gatAllTradingPairs(): Single<List<TradingPair>>
}
