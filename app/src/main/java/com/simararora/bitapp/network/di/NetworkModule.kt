package com.simararora.bitapp.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.simararora.bitapp.BuildConfig
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.deserializer.TradeResponseDeserializer
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.deserializer.TradingPairDetailResponseDeserializer
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.TradeResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.TradingPairDetailResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.deserializer.TickerResponseDeserializer
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.model.TickerResponse
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(
        tickerResponseDeserializer: TickerResponseDeserializer,
        tradingPairDetailResponseDeserializer: TradingPairDetailResponseDeserializer,
        tradeResponseDeserializer: TradeResponseDeserializer
    ): Gson {
        return GsonBuilder()
            .registerTypeAdapter(
                TickerResponse::class.java,
                tickerResponseDeserializer
            ).registerTypeAdapter(
                TradingPairDetailResponse::class.java,
                tradingPairDetailResponseDeserializer
            ).registerTypeAdapter(
                TradeResponse::class.java,
                tradeResponseDeserializer
            ).create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BuildConfig.SERVER_URL)
            .build()
    }
}
