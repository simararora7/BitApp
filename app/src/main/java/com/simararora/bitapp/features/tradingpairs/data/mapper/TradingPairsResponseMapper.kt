package com.simararora.bitapp.features.tradingpairs.data.mapper

import com.simararora.bitapp.common.Mapper
import com.simararora.bitapp.features.tradingpairs.data.model.TickersResponse.TradingPairsResponse
import com.simararora.bitapp.features.tradingpairs.di.TradingPairsScope
import com.simararora.bitapp.features.tradingpairs.domain.model.TradingPairs
import javax.inject.Inject

@TradingPairsScope
class TradingPairsResponseMapper @Inject constructor(): Mapper<TradingPairsResponse, TradingPairs>() {
    override fun map(input: TradingPairsResponse): TradingPairs {
        return with(input) {
            TradingPairs(
                symbol = symbol,
                lastPrice = lastPrice,
                dailyChangeRelative = dailyChangeRelative
            )
        }
    }
}
