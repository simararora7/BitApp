package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.deserializer

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.TradeResponse
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TradeResponseDeserializer @Inject constructor() :
    JsonDeserializer<TradeResponse?> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): TradeResponse? {
        if (context != null && json != null) {
            val jsonArray = json.asJsonArray
            if (jsonArray.size() == 3) {
                val fieldsJson = jsonArray.elementAt(2)
                if (fieldsJson is JsonArray) {
                    val fields: List<Double> = context.deserialize(
                        fieldsJson.asJsonArray,
                        object : TypeToken<List<Number>>() {}.type
                    )
                    return TradeResponse(
                        id = fields[0].toLong(),
                        timestamp = fields[1].toLong(),
                        amount = fields[2],
                        price = fields[3]
                    )
                }
            }
        }
        return null
    }
}
