package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.local

import com.nhaarman.mockitokotlin2.whenever
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.TradeResponse
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TradesStoreTest {

    private lateinit var tradesStore: TradesStore

    @Before
    fun setUp() {
        tradesStore = TradesStore()
    }

    @Test
    fun `should store maximum 10 trades and discard the oldest one when 11th trade is added`() {
        with(tradesStore) {
            addTrade(getMockTradeWithId(1))
            addTrade(getMockTradeWithId(2))
            addTrade(getMockTradeWithId(3))
            addTrade(getMockTradeWithId(4))
            addTrade(getMockTradeWithId(5))
            addTrade(getMockTradeWithId(6))
            addTrade(getMockTradeWithId(7))
            addTrade(getMockTradeWithId(8))
            addTrade(getMockTradeWithId(9))
            addTrade(getMockTradeWithId(10))

            with(getAllTrades()) {
                assertEquals(this[0].id, 1)
                assertEquals(this[1].id, 2)
                assertEquals(this[2].id, 3)
                assertEquals(this[3].id, 4)
                assertEquals(this[4].id, 5)
                assertEquals(this[5].id, 6)
                assertEquals(this[6].id, 7)
                assertEquals(this[7].id, 8)
                assertEquals(this[8].id, 9)
                assertEquals(this[9].id, 10)
            }

            addTrade(getMockTradeWithId(11))

            with(getAllTrades()) {
                assertEquals(this[0].id, 2)
                assertEquals(this[1].id, 3)
                assertEquals(this[2].id, 4)
                assertEquals(this[3].id, 5)
                assertEquals(this[4].id, 6)
                assertEquals(this[5].id, 7)
                assertEquals(this[6].id, 8)
                assertEquals(this[7].id, 9)
                assertEquals(this[8].id, 10)
                assertEquals(this[9].id, 11)
            }

            clear()

            assertTrue { getAllTrades().isEmpty() }
        }
    }

    private fun getMockTradeWithId(id: Long): TradeResponse {
        val trade = Mockito.mock(TradeResponse::class.java)
        whenever(trade.id).thenReturn(id)
        return trade
    }
}
