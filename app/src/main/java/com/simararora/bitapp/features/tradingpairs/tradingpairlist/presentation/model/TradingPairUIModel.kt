package com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.model

import androidx.annotation.StyleRes

data class TradingPairUIModel(
    val symbol: String,
    val formattedSymbol: String,
    val lastPrice: String,
    val dailyChangeRelative: String,
    @StyleRes val bodyTextStyle: Int
)
