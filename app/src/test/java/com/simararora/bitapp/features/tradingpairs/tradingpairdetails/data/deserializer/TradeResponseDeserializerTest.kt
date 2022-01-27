package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.deserializer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.TradeResponse
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TradeResponseDeserializerTest {

    private lateinit var gson: Gson

    @Before
    fun setUp() {
        gson = GsonBuilder()
            .registerTypeAdapter(TradeResponse::class.java, TradeResponseDeserializer())
            .create()
    }

    @Test
    fun `should deserialize null message to null`() {
        assertNull(gson.fromJson(null as String?, TradeResponse::class.java))
    }

    @Test
    fun `should deserialize trade message to TradeResponse`() {
        val tradeJson = """
            [276,"te",[983081594,1643290413779,0.136489,36666]]
        """.trimIndent()
        assertEquals(
            gson.fromJson(tradeJson, TradeResponse::class.java),
            TradeResponse(
                id = 983081594,
                timestamp = 1643290413779,
                amount = 0.136489,
                price = 36666.0
            )
        )
    }

    @Test
    fun `should deserialize all other messages to null`() {
        val tradeJson = """
            [276,"hb"]
        """.trimIndent()
        assertNull(gson.fromJson(tradeJson, TradeResponse::class.java))
    }
}
