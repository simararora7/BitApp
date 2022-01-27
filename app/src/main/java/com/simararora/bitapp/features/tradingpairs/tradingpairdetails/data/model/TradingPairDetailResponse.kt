package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model

data class TradingPairDetailResponse(
    val bid: Double,
    val bidSize: Double,
    val ask: Double,
    val askSize: Double,
    val dailyChange: Double,
    val dailyChangeRelative: Double,
    val lastPrice: Double,
    val volume: Double,
    val high: Double,
    val low: Double
)
