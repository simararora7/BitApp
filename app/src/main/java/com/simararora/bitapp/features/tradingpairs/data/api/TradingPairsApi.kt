package com.simararora.bitapp.features.tradingpairs.data.api

import com.simararora.bitapp.features.tradingpairs.data.model.TickersResponse
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface TradingPairsApi {

    @GET("/v2/tickers")
    fun getTradingPairs(
        @Query(QUERY_KEY_SYMBOLS) symbols: String
    ): Single<List<TickersResponse>>

    companion object {
        const val QUERY_KEY_SYMBOLS = "symbols"

        fun build(retrofit: Retrofit): TradingPairsApi =
            retrofit.create(TradingPairsApi::class.java)

    }
}
