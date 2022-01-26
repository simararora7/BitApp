package com.simararora.bitapp.features.tradingpairs.domain.model

data class TradingPairs(
    val symbol: String,
    val lastPrice: Double,
    val dailyChangeRelative: Double
)
