package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.mapper

import com.simararora.bitapp.R
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.Trade
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.model.TradeUiModel
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class TradeUIModelMapperTest {

    private lateinit var mapper: TradeUIModelMapper

    @Before
    fun setUp() {
        mapper = TradeUIModelMapper()
    }

    @Test
    fun testMap() {
        val input = listOf(
            Trade(
                id = 1L,
                timestamp = 1643291602776,
                price = 3.12346,
                amount = 5.012345
            ),
            Trade(
                id = 2L,
                timestamp = 1643291711250,
                price = 3.12346,
                amount = -5.012345
            ),
            Trade(
                id = 3L,
                timestamp = 1643291742429,
                price = 3.12346,
                amount = 0.0
            )
        )

        val output = listOf(
            TradeUiModel(
                id = 1L,
                amount = "5.0123",
                price = "3.1235",
                displayTime = "07:23:22 PM",
                priceTextStyle = R.style.BitAppText_B1_Positive
            ),
            TradeUiModel(
                id = 2L,
                amount = "-5.0123",
                price = "3.1235",
                displayTime = "07:25:11 PM",
                priceTextStyle = R.style.BitAppText_B1_Negative
            ),
            TradeUiModel(
                id = 3L,
                amount = "0.0000",
                price = "3.1235",
                displayTime = "07:25:42 PM",
                priceTextStyle = R.style.BitAppText_B1
            )
        )

        assertEquals(
            mapper.mapList(input),
            output
        )
    }
}
