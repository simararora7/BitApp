package com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.mapper

import com.simararora.bitapp.R
import com.simararora.bitapp.common.Mapper
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.di.TradingPairListScope
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model.TradingPair
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.model.TradingPairUIModel
import javax.inject.Inject

@TradingPairListScope
class TradingPairViewModelMapper @Inject constructor() :
    Mapper<TradingPair, TradingPairUIModel>() {
    override fun map(input: TradingPair): TradingPairUIModel {
        return with(input) {
            TradingPairUIModel(
                symbol = symbol,
                formattedSymbol = formatSymbol(symbol),
                dailyChangeRelative = formatDailyChangeRelative(dailyChangeRelative),
                lastPrice = formatLastPrice(lastPrice),
                bodyTextStyle = getBodyTextStyle(dailyChangeRelative)
            )
        }
    }

    private fun formatSymbol(rawSymbol: String): String {
        val symbol = rawSymbol.substring(1)
        return when {
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
