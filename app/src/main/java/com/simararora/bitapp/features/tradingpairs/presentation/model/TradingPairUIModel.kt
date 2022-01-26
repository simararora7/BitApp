package com.simararora.bitapp.features.tradingpairs.presentation.model

data class TradingPairUIModel(
    val symbol: String,
    val lastPrice: Double,
    val dailyChangeRelative: Double
)
