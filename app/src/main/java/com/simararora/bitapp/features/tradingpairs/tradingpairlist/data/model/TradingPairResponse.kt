package com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.model

sealed class TickerResponse {

    data class TradingPairsResponse(
        val symbol: String,
        val lastPrice: Double,
        val dailyChangeRelative: Double
    ) : TickerResponse()

    object Unknown : TickerResponse()
}
