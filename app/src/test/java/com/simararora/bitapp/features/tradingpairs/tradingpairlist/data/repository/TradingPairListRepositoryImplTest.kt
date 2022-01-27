package com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.repository

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.simararora.bitapp.features.tradingpairs.common.data.TradingPairListInMemoryCache
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.api.TradingPairListApi
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.mapper.TradingPairResponseMapper
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.model.TickerResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.model.TickerResponse.TradingPairsResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model.TradingPair
import io.reactivex.Single
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TradingPairListRepositoryImplTest {

    @Mock
    private lateinit var tradingPairListApi: TradingPairListApi

    @Mock
    private lateinit var tradingPairResponseMapper: TradingPairResponseMapper

    @Mock
    private lateinit var tradingPairListInMemoryCache: TradingPairListInMemoryCache

    @InjectMocks
    private lateinit var repository: TradingPairListRepositoryImpl

    @Test
    fun `should get trading pairs from api, filter unknowns, update cache and return domain models`() {
        val symbol = "TEST"

        val tradingPairResponse1 = Mockito.mock(TradingPairsResponse::class.java)
        val tradingPairResponse2 = Mockito.mock(TradingPairsResponse::class.java)

        val tradingPair1 = Mockito.mock(TradingPair::class.java)
        val tradingPair2 = Mockito.mock(TradingPair::class.java)

        whenever(
            tradingPairResponseMapper.mapList(listOf(tradingPairResponse1, tradingPairResponse2))
        ).thenReturn(
            listOf(tradingPair1, tradingPair2)
        )

        whenever(tradingPairListApi.getTradingPairs(symbol))
            .thenReturn(
                Single.just(
                    listOf(
                        TickerResponse.Unknown,
                        tradingPairResponse1,
                        TickerResponse.Unknown,
                        tradingPairResponse2,
                        TickerResponse.Unknown,
                    )
                )
            )

        repository.getTradingPairList(symbol)
            .test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValueAt(0, listOf(tradingPair1, tradingPair2))

        verify(tradingPairListApi).getTradingPairs(symbol)
        verify(tradingPairResponseMapper).mapList(
            listOf(tradingPairResponse1, tradingPairResponse2)
        )
        verify(tradingPairListInMemoryCache).updateCache(
            listOf(tradingPairResponse1, tradingPairResponse2)
        )
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(
            tradingPairListApi,
            tradingPairResponseMapper,
            tradingPairListInMemoryCache
        )
    }
}
