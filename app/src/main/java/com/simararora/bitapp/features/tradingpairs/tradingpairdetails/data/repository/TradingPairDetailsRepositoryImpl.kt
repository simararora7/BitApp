package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.repository

import com.simararora.bitapp.features.tradingpairs.common.data.TradingPairListInMemoryCache
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.local.TradesStore
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.mapper.TradeResponseMapper
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.mapper.TradingPairDetailResponseMapper
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.service.TradesWebSocketService
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.service.TradingPairWebSocketService
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.di.TradingPairDetailScope
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.Trade
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.TradingPairDetail
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.repository.TradingPairDetailsRepository
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.mapper.TradingPairResponseMapper
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model.TradingPair
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

@TradingPairDetailScope
class TradingPairDetailsRepositoryImpl @Inject constructor(
    private val tradingPairWebSocketService: TradingPairWebSocketService,
    private val tradesWebSocketService: TradesWebSocketService,
    private val tradingPairDetailResponseMapper: TradingPairDetailResponseMapper,
    private val tradingPairResponseMapper: TradingPairResponseMapper,
    private val tradeStore: TradesStore,
    private val tradeResponseMapper: TradeResponseMapper,
    private val tradingPairListInMemoryCache: TradingPairListInMemoryCache
) : TradingPairDetailsRepository {

    override fun getTradingPairDetails(symbol: String): Observable<TradingPairDetail> {
        return tradingPairWebSocketService.observeTradingPairChanges(symbol)
            .map(tradingPairDetailResponseMapper::map)
    }

    override fun getTopTrades(symbol: String): Observable<List<Trade>> {
        return Observable.fromCallable {
            tradeStore.clear()
        }.flatMap { tradesWebSocketService.observeTradeChanges(symbol) }
            .map { trade ->
                tradeStore.addTrade(trade)
                tradeStore.getAllTrades()
            }.map(tradeResponseMapper::mapList)
    }

    override fun gatAllTradingPairs(): Single<List<TradingPair>> {
        return Single.fromCallable { tradingPairListInMemoryCache.getCachedTradingPairList() }
            .map(tradingPairResponseMapper::mapList)

    }
}
