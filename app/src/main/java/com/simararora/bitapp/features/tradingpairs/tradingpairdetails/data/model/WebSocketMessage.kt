package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model

import com.google.gson.annotations.SerializedName

data class WebSocketMessage(
    @field:SerializedName("event") val event: String,
    @field:SerializedName("channel") val channel: String,
    @field:SerializedName("symbol") val symbol: String
)
