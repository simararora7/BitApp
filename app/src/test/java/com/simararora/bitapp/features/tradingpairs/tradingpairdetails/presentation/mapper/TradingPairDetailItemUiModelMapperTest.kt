package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.mapper

import com.simararora.bitapp.R
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.TradingPairDetail
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.model.TradingPairDetailItemUiModel
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class TradingPairDetailItemUiModelMapperTest {

    private lateinit var mapper: TradingPairDetailItemUiModelMapper

    @Before
    fun setUp() {
        mapper = TradingPairDetailItemUiModelMapper()
    }

    @Test
    fun `test map when daily change is negative`() {
        val input = TradingPairDetail(
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

        val output = listOf(
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_open_price,
                value = "-1683.0656",
                valueTextStyle = R.style.BitAppText_B1
            ),
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_daily_change,
                value = "-1683.0656 (-4.40%)",
                valueTextStyle = R.style.BitAppText_B1_Negative
            ),
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_top_bid,
                value = "36603.0000",
                valueTextStyle = R.style.BitAppText_B1_Positive
            ),
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_top_ask,
                value = "36604.0000",
                valueTextStyle = R.style.BitAppText_B1_Negative
            ),
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_last_price,
                value = "36606.0000",
                valueTextStyle = R.style.BitAppText_B1
            ),
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_24h_range,
                value = "35557.0000 - 38935.0000",
                valueTextStyle = R.style.BitAppText_B1
            )
        )

        assertEquals(mapper.map(input), output)
    }

    @Test
    fun `test map when daily change is positive`() {
        val input = TradingPairDetail(
            bid = 36603.0,
            bidSize = 6.984008229999998,
            ask = 36604.0,
            askSize = 15.796733140000002,
            dailyChange = 1683.06558394,
            dailyChangeRelative = 0.044,
            lastPrice = 36606.0,
            volume = 10509.83484985,
            high = 38935.0,
            low = 35557.0
        )

        val output = listOf(
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_open_price,
                value = "1683.0656",
                valueTextStyle = R.style.BitAppText_B1
            ),
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_daily_change,
                value = "1683.0656 (+4.40%)",
                valueTextStyle = R.style.BitAppText_B1_Positive
            ),
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_top_bid,
                value = "36603.0000",
                valueTextStyle = R.style.BitAppText_B1_Positive
            ),
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_top_ask,
                value = "36604.0000",
                valueTextStyle = R.style.BitAppText_B1_Negative
            ),
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_last_price,
                value = "36606.0000",
                valueTextStyle = R.style.BitAppText_B1
            ),
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_24h_range,
                value = "35557.0000 - 38935.0000",
                valueTextStyle = R.style.BitAppText_B1
            )
        )

        assertEquals(mapper.map(input), output)
    }

    @Test
    fun `test map when daily change is zero`() {
        val input = TradingPairDetail(
            bid = 36603.0,
            bidSize = 6.984008229999998,
            ask = 36604.0,
            askSize = 15.796733140000002,
            dailyChange = 1683.06558394,
            dailyChangeRelative = 0.0,
            lastPrice = 36606.0,
            volume = 10509.83484985,
            high = 38935.0,
            low = 35557.0
        )

        val output = listOf(
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_open_price,
                value = "1683.0656",
                valueTextStyle = R.style.BitAppText_B1
            ),
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_daily_change,
                value = "1683.0656 (+0.00%)",
                valueTextStyle = R.style.BitAppText_B1
            ),
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_top_bid,
                value = "36603.0000",
                valueTextStyle = R.style.BitAppText_B1_Positive
            ),
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_top_ask,
                value = "36604.0000",
                valueTextStyle = R.style.BitAppText_B1_Negative
            ),
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_last_price,
                value = "36606.0000",
                valueTextStyle = R.style.BitAppText_B1
            ),
            TradingPairDetailItemUiModel(
                labelRes = R.string.trade_detail_24h_range,
                value = "35557.0000 - 38935.0000",
                valueTextStyle = R.style.BitAppText_B1
            )
        )

        assertEquals(mapper.map(input), output)
    }
}
