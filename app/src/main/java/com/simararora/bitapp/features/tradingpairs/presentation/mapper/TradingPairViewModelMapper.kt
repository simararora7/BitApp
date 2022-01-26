package com.simararora.bitapp.features.tradingpairs.presentation.mapper

import com.simararora.bitapp.R
import com.simararora.bitapp.common.Mapper
import com.simararora.bitapp.features.tradingpairs.di.TradingPairsScope
import com.simararora.bitapp.features.tradingpairs.domain.model.TradingPairs
import com.simararora.bitapp.features.tradingpairs.presentation.model.TradingPairUIModel
import javax.inject.Inject

@TradingPairsScope
class TradingPairViewModelMapper @Inject constructor() :
    Mapper<TradingPairs, TradingPairUIModel>() {
    override fun map(input: TradingPairs): TradingPairUIModel {
        return with(input) {
            TradingPairUIModel(
                symbol = formatSymbol(symbol),
                dailyChangeRelative = formatDailyChangeRelative(dailyChangeRelative),
                lastPrice = formatLastPrice(lastPrice),
                bodyTextStyle = getBodyTextStyle(dailyChangeRelative)
            )
        }
    }

    private fun formatSymbol(symbol: String): String = when {
        symbol.contains(":") -> {
            symbol.replace(":", "/")
        }
        symbol.length == 6 -> {
            "${symbol.substring(0, 3)}/${symbol.substring(3, 6)}"
        }
        else -> {
            symbol
        }
    }

    private fun formatDailyChangeRelative(dailyChangeRelative: Double): String {
        val percentage = if (dailyChangeRelative > 0) {
            dailyChangeRelative * 100
        } else {
            -1 * dailyChangeRelative * 100
        }
        return String.format("%.2f%%", percentage)
    }

    private fun formatLastPrice(lastPrice: Double): String {
        return String.format("%.4f", lastPrice)
    }

    private fun getBodyTextStyle(dailyChangeRelative: Double) = when {
        dailyChangeRelative > 0 -> R.style.BitAppText_B1_Positive
        dailyChangeRelative < 0 -> R.style.BitAppText_B1_Negative
        else -> R.style.BitAppText_B1
    }
}
