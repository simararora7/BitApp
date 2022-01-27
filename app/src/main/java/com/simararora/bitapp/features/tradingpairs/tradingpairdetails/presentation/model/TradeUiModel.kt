package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.model

import androidx.annotation.StyleRes

data class TradeUiModel(
    val id: Long,
    val amount: String,
    val price: String,
    val displayTime: String,
    @StyleRes val priceTextStyle: Int
)
