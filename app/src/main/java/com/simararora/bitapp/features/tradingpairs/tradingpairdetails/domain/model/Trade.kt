package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model

data class Trade(
    val id: Long,
    val timestamp: Long,
    val amount: Double,
    val price: Double
)
