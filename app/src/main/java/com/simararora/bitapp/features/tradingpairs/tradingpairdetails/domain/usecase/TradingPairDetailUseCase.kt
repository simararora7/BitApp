package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.usecase

import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.di.TradingPairDetailScope
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.Trade
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.TradingPairDetail
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.repository.TradingPairDetailsRepository
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model.TradingPair
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

interface TradingPairDetailUseCase {
    fun getTradingPairDetails(symbol: String): Observable<TradingPairDetail>
    fun getTopTrades(symbol: String): Observable<List<Trade>>
    fun gatAllTradingPairs(): Single<List<TradingPair>>
}

@TradingPairDetailScope
class TradingPairDetailUseCaseImpl @Inject constructor(
    private val tradingPairDetailsRepository: TradingPairDetailsRepository
) : TradingPairDetailUseCase {

    override fun getTradingPairDetails(symbol: String): Observable<TradingPairDetail> {
        return tradingPairDetailsRepository.getTradingPairDetails(symbol)
    }

    override fun getTopTrades(symbol: String): Observable<List<Trade>> {
        return tradingPairDetailsRepository.getTopTrades(symbol)
            .map { trades ->
                trades.sortedByDescending { trade ->
                    trade.timestamp
                }
            }
    }

    override fun gatAllTradingPairs(): Single<List<TradingPair>> {
        return tradingPairDetailsRepository.gatAllTradingPairs()
    }
}
