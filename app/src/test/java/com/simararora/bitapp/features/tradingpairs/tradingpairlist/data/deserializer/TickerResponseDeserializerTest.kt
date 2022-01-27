package com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.deserializer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.model.TickerResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.model.TickerResponse.TradingPairsResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.model.TickerResponse.Unknown
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TickerResponseDeserializerTest {

    private lateinit var gson: Gson

    private val testResponseTradingPair: String = """
        [
          "tBTCUSD",
          10654,
          53.62425959,
          10655,
          76.68743116,
          745.1,
          0.0752,
          10655,
          14420.34271146,
          10766,
          9889.1449809
        ]
    """.trimIndent()

    private val testResponseFunding: String = """
        [
          "fUSD",
          0.0003447013698630137,
          0.000316,
          30,
          1682003.0922634401,
          0.00031783,
          4,
          23336.958053110004,
          0.00000707,
          0.0209,
          0.0003446,
          156123478.78447533,
          0.000347,
          0.00009,
          null,
          null,
          146247919.52883354
        ]
    """.trimIndent()

    @Before
    fun setUp() {
        gson = GsonBuilder()
            .registerTypeAdapter(TickerResponse::class.java, TickerResponseDeserializer())
            .create()
    }

    @Test
    fun `should deserialize trading pair into TradingPairsResponse`() {
        val deserialized = gson.fromJson(testResponseTradingPair, TickerResponse::class.java)
        assertTrue { deserialized is TradingPairsResponse }
        with(deserialized as TradingPairsResponse) {
            assertEquals(symbol, "tBTCUSD")
            assertEquals(lastPrice, 10655.0)
            assertEquals(dailyChangeRelative, 0.0752)
        }
    }

    @Test
    fun `should deserialize funding into Unknown`() {
        val deserialized = gson.fromJson(testResponseFunding, TickerResponse::class.java)
        assertTrue { deserialized is Unknown }
    }
}
