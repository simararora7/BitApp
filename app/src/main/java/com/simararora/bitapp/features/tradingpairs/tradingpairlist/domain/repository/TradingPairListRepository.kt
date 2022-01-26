package com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.repository

import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model.TradingPair
import io.reactivex.Single

interface TradingPairListRepository {
    fun getTradingPairList(symbols: String): Single<List<TradingPair>>
}
