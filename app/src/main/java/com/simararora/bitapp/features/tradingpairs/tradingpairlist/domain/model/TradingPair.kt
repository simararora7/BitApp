package com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model

data class TradingPair(
    val symbol: String,
    val lastPrice: Double,
    val dailyChangeRelative: Double
)
