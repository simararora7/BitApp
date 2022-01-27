package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.mapper

import com.simararora.bitapp.R
import com.simararora.bitapp.common.Mapper
import com.simararora.bitapp.common.utils.TimeFormat
import com.simararora.bitapp.common.utils.formatCurrency
import com.simararora.bitapp.common.utils.formatTime
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.di.TradingPairDetailScope
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.Trade
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.model.TradeUiModel
import javax.inject.Inject

@TradingPairDetailScope
class TradeUIModelMapper @Inject constructor() : Mapper<Trade, TradeUiModel>() {
    override fun map(input: Trade): TradeUiModel {
        return with(input) {
            TradeUiModel(
                id = id,
                amount = formatCurrency(amount),
                price = formatCurrency(price),
                displayTime = formatTime(TimeFormat.HH_MM_SS_AA, timestamp),
                priceTextStyle = getPriceTextStyle(amount)
            )
        }
    }

    private fun getPriceTextStyle(amount: Double): Int {
        return when {
            amount > 0 -> R.style.BitAppText_B1_Positive
            amount < 0 -> R.style.BitAppText_B1_Negative
            else -> R.style.BitAppText_B1
        }
    }
}
