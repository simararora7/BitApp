package com.simararora.bitapp.features.tradingpairs.presentation.mapper

import com.simararora.bitapp.common.Mapper
import com.simararora.bitapp.features.tradingpairs.di.TradingPairsScope
import com.simararora.bitapp.features.tradingpairs.domain.model.TradingPairs
import com.simararora.bitapp.features.tradingpairs.presentation.model.TradingPairUIModel
import javax.inject.Inject

@TradingPairsScope
class TradingPairViewModelMapper @Inject constructor() : Mapper<TradingPairs, TradingPairUIModel>() {
    override fun map(input: TradingPairs): TradingPairUIModel {
        return with(input) {
            TradingPairUIModel(
                symbol = symbol,
                dailyChangeRelative = dailyChangeRelative,
                lastPrice = lastPrice
            )
        }
    }
}
