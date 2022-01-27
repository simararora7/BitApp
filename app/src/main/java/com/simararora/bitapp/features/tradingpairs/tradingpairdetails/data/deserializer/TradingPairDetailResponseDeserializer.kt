package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.deserializer

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.TradingPairDetailResponse
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TradingPairDetailResponseDeserializer @Inject constructor() :
    JsonDeserializer<TradingPairDetailResponse?> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): TradingPairDetailResponse? {
        if (context != null && json != null) {
            val jsonArray = json.asJsonArray
            if (jsonArray.size() == 2) {
                val fieldsJson = jsonArray.elementAt(1)
                if (fieldsJson is JsonArray) {
                    val fields: List<Double> = context.deserialize(
                        fieldsJson.asJsonArray,
                        object : TypeToken<List<Double>>() {}.type
                    )
                    return TradingPairDetailResponse(
                        bid = fields[0],
                        bidSize = fields[1],
                        ask = fields[2],
                        askSize = fields[3],
                        dailyChange = fields[4],
                        dailyChangeRelative = fields[5],
                        lastPrice = fields[6],
                        volume = fields[7],
                        high = fields[8],
                        low = fields[9],
                    )
                }
            }
        }
        return null
    }
}
