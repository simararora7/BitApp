package com.simararora.bitapp.features.tradingpairs.data.model

sealed class TickersResponse {

    data class TradingPairsResponse(
        val symbol: String,
        val lastPrice: Double,
        val dailyChangeRelative: Double
    ) : TickersResponse()

    object Unknown : TickersResponse()
}
