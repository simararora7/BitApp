package com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.mapper

import com.simararora.bitapp.R
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model.TradingPair
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.model.TradingPairUIModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class TradingPairViewModelMapperTest {

    private lateinit var mapper: TradingPairViewModelMapper

    @Before
    fun setUp() {
        mapper = TradingPairViewModelMapper()
    }

    @Test
    fun testMapList() {
        val input = listOf(
            TradingPair(
                symbol = "tBTCUSD",
                dailyChangeRelative = 0.02345,
                lastPrice = 123.456789
            ),
            TradingPair(
                symbol = "tABCDE:FGH",
                dailyChangeRelative = -0.01011,
                lastPrice = 123.456729
            ),
            TradingPair(
                symbol = "tRANDOMSYMBOL",
                dailyChangeRelative = 0.0,
                lastPrice = 123.456729
            )
        )

        val output = listOf(
            TradingPairUIModel(
                symbol = "tBTCUSD",
                formattedSymbol = "BTC/USD",
                dailyChangeRelative = "2.34%",
                lastPrice = "123.4568",
                bodyTextStyle = R.style.BitAppText_B1_Positive
            ),
            TradingPairUIModel(
                symbol = "tABCDE:FGH",
                formattedSymbol = "ABCDE/FGH",
                dailyChangeRelative = "1.01%",
                lastPrice = "123.4567",
                bodyTextStyle = R.style.BitAppText_B1_Negative
            ),
            TradingPairUIModel(
                symbol = "tRANDOMSYMBOL",
                formattedSymbol = "RANDOMSYMBOL",
                dailyChangeRelative = "0.00%",
                lastPrice = "123.4567",
                bodyTextStyle = R.style.BitAppText_B1
            )
        )

        assertEquals(mapper.mapList(input), output)
    }
}
