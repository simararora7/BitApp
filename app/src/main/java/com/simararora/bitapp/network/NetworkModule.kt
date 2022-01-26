package com.simararora.bitapp.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.simararora.bitapp.BuildConfig
import com.simararora.bitapp.features.tradingpairs.data.deserializer.TickersResponseDeserializer
import com.simararora.bitapp.features.tradingpairs.data.model.TickersResponse
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
        tickersResponseDeserializer: TickersResponseDeserializer
    ): Gson {
        return GsonBuilder()
            .registerTypeAdapter(TickersResponse::class.java, tickersResponseDeserializer)
            .create()
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
