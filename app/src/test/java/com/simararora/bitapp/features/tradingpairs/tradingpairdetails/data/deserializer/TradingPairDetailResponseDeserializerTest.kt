package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.deserializer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.TradingPairDetailResponse
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TradingPairDetailResponseDeserializerTest {

    private lateinit var gson: Gson

    @Before
    fun setUp() {
        gson = GsonBuilder()
            .registerTypeAdapter(
                TradingPairDetailResponse::class.java,
                TradingPairDetailResponseDeserializer()
            ).create()
    }

    @Test
    fun `should deserialize null message to null`() {
        assertNull(gson.fromJson(null as String?, TradingPairDetailResponse::class.java))
    }

    @Test
    fun `should deserialize trade message to TradingPairDetailResponse`() {
        val tickerJson = """
            [31241,[36603,6.984008229999998,36604,15.796733140000002,-1683.06558394,-0.044,36606,10509.83484985,38935,35557]]
        """.trimIndent()
        assertEquals(
            gson.fromJson(tickerJson, TradingPairDetailResponse::class.java),
            TradingPairDetailResponse(
                bid = 36603.0,
                bidSize = 6.984008229999998,
                ask = 36604.0,
                askSize = 15.796733140000002,
                dailyChange = -1683.06558394,
                dailyChangeRelative = -0.044,
                lastPrice = 36606.0,
                volume = 10509.83484985,
                high = 38935.0,
                low = 35557.0
            )
        )
    }

    @Test
    fun `should deserialize all other messages to null`() {
        val tickerJson = """
           [31241,"hb"]
        """.trimIndent()
        assertNull(gson.fromJson(tickerJson, TradingPairDetailResponse::class.java))
    }
}
