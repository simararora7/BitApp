package com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.api

import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.model.TickerResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface TradingPairListApi {

    @GET("/v2/tickers")
    fun getTradingPairs(
        @Query(QUERY_KEY_SYMBOLS) symbols: String
    ): Single<List<TickerResponse>>

    companion object {
        const val QUERY_KEY_SYMBOLS = "symbols"

        fun build(retrofit: Retrofit): TradingPairListApi =
            retrofit.create(TradingPairListApi::class.java)

    }
}
