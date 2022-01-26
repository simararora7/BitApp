package com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.mapper

import com.simararora.bitapp.common.Mapper
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.model.TickerResponse.TradingPairsResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.di.TradingPairListScope
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model.TradingPair
import javax.inject.Inject

@TradingPairListScope
class TradingPairResponseMapper @Inject constructor(): Mapper<TradingPairsResponse, TradingPair>() {
    override fun map(input: TradingPairsResponse): TradingPair {
        return with(input) {
            TradingPair(
                symbol = symbol,
                lastPrice = lastPrice,
                dailyChangeRelative = dailyChangeRelative
            )
        }
    }
}
