package com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.mapper

import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.model.TickerResponse.TradingPairsResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model.TradingPair
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class TradingPairResponseMapperTest {

    private lateinit var mapper: TradingPairResponseMapper

    @Before
    fun setUp() {
        mapper = TradingPairResponseMapper()
    }

    @Test
    fun testMap() {
        assertEquals(
            mapper.map(
                TradingPairsResponse(
                    symbol = "Symbol",
                    lastPrice = 1.0,
                    dailyChangeRelative = 0.01
                )
            ),
            TradingPair(
                symbol = "Symbol",
                lastPrice = 1.0,
                dailyChangeRelative = 0.01
            )
        )
    }
}
