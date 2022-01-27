package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.mapper

import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.TradeResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.Trade
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class TradeResponseMapperTest {

    private lateinit var mapper: TradeResponseMapper

    @Before
    fun setUp() {
        mapper = TradeResponseMapper()
    }

    @Test
    fun testMap() {
        assertEquals(
            mapper.map(
                TradeResponse(
                    id = 1L,
                    timestamp = 123L,
                    amount = 1.2,
                    price = 2.3
                )
            ),
            Trade(
                id = 1L,
                timestamp = 123L,
                amount = 1.2,
                price = 2.3
            )
        )
    }
}
