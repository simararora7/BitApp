package com.simararora.bitapp.features.tradingpairs.data.repository

import com.simararora.bitapp.features.tradingpairs.data.api.TradingPairsApi
import com.simararora.bitapp.features.tradingpairs.data.mapper.TradingPairsResponseMapper
import com.simararora.bitapp.features.tradingpairs.data.model.TickersResponse.TradingPairsResponse
import com.simararora.bitapp.features.tradingpairs.di.TradingPairsScope
import com.simararora.bitapp.features.tradingpairs.domain.model.TradingPairs
import com.simararora.bitapp.features.tradingpairs.domain.repository.TradingPairsRepository
import io.reactivex.Single
import javax.inject.Inject

@TradingPairsScope
class TradingPairsRepositoryImpl @Inject constructor(
    private val tradingPairsApi: TradingPairsApi,
    private val tradingPairsResponseMapper: TradingPairsResponseMapper
) : TradingPairsRepository {
    override fun getTradingPairs(symbols: String): Single<List<TradingPairs>> {
        return tradingPairsApi.getTradingPairs(symbols).map { items ->
            val result = mutableListOf<TradingPairsResponse>()
            items.forEach { ticker ->
                if (ticker is TradingPairsResponse) {
                    result.add(ticker)
                }
            }
            result
        }.map(tradingPairsResponseMapper::mapList)
    }
}
