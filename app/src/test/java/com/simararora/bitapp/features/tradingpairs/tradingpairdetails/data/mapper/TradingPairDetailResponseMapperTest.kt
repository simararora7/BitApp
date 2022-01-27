package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.mapper

import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.TradingPairDetailResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.TradingPairDetail
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TradingPairDetailResponseMapperTest {

    private lateinit var mapper: TradingPairDetailResponseMapper

    @Before
    fun setUp() {
        mapper = TradingPairDetailResponseMapper()
    }

    @Test
    fun testMap() {
        assertEquals(
            mapper.map(
                TradingPairDetailResponse(
                    bid = 1.1,
                    bidSize = 2.2,
                    ask = 3.3,
                    askSize = 4.4,
                    dailyChange = 5.5,
                    dailyChangeRelative = 6.6,
                    lastPrice = 7.7,
                    volume = 8.8,
                    high = 9.9,
                    low = 10.10
                )
            ),
            TradingPairDetail(
                bid = 1.1,
                bidSize = 2.2,
                ask = 3.3,
                askSize = 4.4,
                dailyChange = 5.5,
                dailyChangeRelative = 6.6,
                lastPrice = 7.7,
                volume = 8.8,
                high = 9.9,
                low = 10.10
            )
        )
    }
}
