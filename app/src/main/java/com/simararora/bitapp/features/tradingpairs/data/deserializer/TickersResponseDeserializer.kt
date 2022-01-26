package com.simararora.bitapp.features.tradingpairs.data.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.simararora.bitapp.features.tradingpairs.data.model.TickersResponse
import com.simararora.bitapp.features.tradingpairs.data.model.TickersResponse.TradingPairsResponse
import com.simararora.bitapp.features.tradingpairs.data.model.TickersResponse.Unknown
import java.lang.reflect.Type
import javax.inject.Inject

class TickersResponseDeserializer @Inject constructor() : JsonDeserializer<TickersResponse> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): TickersResponse {
        return if (context != null) {
            val fields =
                context.deserialize<List<Any>>(json, object : TypeToken<List<Any>>() {}.type)
            val symbol = fields[0] as String
            if (symbol.startsWith("t")) {
                TradingPairsResponse(
                    symbol = symbol.substring(1),
                    dailyChangeRelative = fields[6] as Double,
                    lastPrice = fields[7] as Double
                )
            } else {
                Unknown
            }
        } else {
            Unknown
        }
    }
}
