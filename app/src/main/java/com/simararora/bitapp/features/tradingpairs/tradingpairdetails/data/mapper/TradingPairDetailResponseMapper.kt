package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.mapper

import com.simararora.bitapp.common.Mapper
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.TradingPairDetailResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.di.TradingPairDetailScope
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.TradingPairDetail
import javax.inject.Inject

@TradingPairDetailScope
class TradingPairDetailResponseMapper @Inject constructor() :
    Mapper<TradingPairDetailResponse, TradingPairDetail>() {

    override fun map(input: TradingPairDetailResponse): TradingPairDetail {
        return with(input) {
            TradingPairDetail(
                bid = bid,
                bidSize = bidSize,
                ask = ask,
                askSize = askSize,
                dailyChange = dailyChange,
                dailyChangeRelative = dailyChangeRelative,
                lastPrice = lastPrice,
                volume = volume,
                high = high,
                low = low
            )
        }
    }
}
