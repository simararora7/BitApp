package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.mapper

import com.simararora.bitapp.R
import com.simararora.bitapp.common.Mapper
import com.simararora.bitapp.common.utils.formatCurrency
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.di.TradingPairDetailScope
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.TradingPairDetail
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.model.TradingPairDetailItemUiModel
import javax.inject.Inject

@TradingPairDetailScope
class TradingPairDetailItemUiModelMapper @Inject constructor() :
    Mapper<TradingPairDetail, List<TradingPairDetailItemUiModel>>() {
    override fun map(input: TradingPairDetail): List<TradingPairDetailItemUiModel> {
        return with(input) {
            listOf(
                TradingPairDetailItemUiModel(
                    labelRes = R.string.trade_detail_open_price,
                    value = formatCurrency(dailyChange),
                    valueTextStyle = R.style.BitAppText_B1
                ),
                TradingPairDetailItemUiModel(
                    labelRes = R.string.trade_detail_daily_change,
                    value = getDailyChange(input),
                    valueTextStyle = getDailyChangeTextStyle(input.dailyChangeRelative)
                ),
                TradingPairDetailItemUiModel(
                    labelRes = R.string.trade_detail_top_bid,
                    value = formatCurrency(bid),
                    valueTextStyle = R.style.BitAppText_B1_Positive
                ),
                TradingPairDetailItemUiModel(
                    labelRes = R.string.trade_detail_top_ask,
                    value = formatCurrency(ask),
                    valueTextStyle = R.style.BitAppText_B1_Negative
                ),
                TradingPairDetailItemUiModel(
                    labelRes = R.string.trade_detail_last_price,
                    value = formatCurrency(lastPrice),
                    valueTextStyle = R.style.BitAppText_B1
                ),
                TradingPairDetailItemUiModel(
                    labelRes = R.string.trade_detail_24h_range,
                    value = get24HourRange(input),
                    valueTextStyle = R.style.BitAppText_B1
                ),
            )
        }
    }

    private fun getDailyChange(detail: TradingPairDetail): String {
        return "${formatCurrency(detail.dailyChange)} (${formatDailyChangeRelative(detail.dailyChangeRelative)})"
    }

    private fun formatDailyChangeRelative(dailyChangeRelative: Double): String {
        return if (dailyChangeRelative >= 0) {
            val percentage = dailyChangeRelative * 100
            String.format("+%.2f%%", percentage)
        } else {
            val percentage = -1 * dailyChangeRelative * 100
            String.format("-%.2f%%", percentage)
        }
    }

    private fun get24HourRange(detail: TradingPairDetail): String {
        return "${formatCurrency(detail.low)} - ${formatCurrency(detail.high)}"
    }

    private fun getDailyChangeTextStyle(dailyChangeRelative: Double) = when {
        dailyChangeRelative > 0 -> R.style.BitAppText_B1_Positive
        dailyChangeRelative < 0 -> R.style.BitAppText_B1_Negative
        else -> R.style.BitAppText_B1
    }
}
