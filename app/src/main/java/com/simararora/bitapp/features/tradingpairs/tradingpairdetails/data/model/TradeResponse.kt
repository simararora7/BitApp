package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model

data class TradeResponse(
    val id: Long,
    val timestamp: Long,
    val amount: Double,
    val price: Double
)
