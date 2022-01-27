package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.mapper

import com.simararora.bitapp.common.Mapper
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.TradeResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.di.TradingPairDetailScope
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.Trade
import javax.inject.Inject

@TradingPairDetailScope
class TradeResponseMapper @Inject constructor() : Mapper<TradeResponse, Trade>() {
    override fun map(input: TradeResponse): Trade {
        return with(input) {
            Trade(
                id = id,
                timestamp = timestamp,
                amount = amount,
                price = price
            )
        }
    }
}
