package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.repository

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.simararora.bitapp.features.tradingpairs.common.data.TradingPairListInMemoryCache
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.local.TradesStore
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.mapper.TradeResponseMapper
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.mapper.TradingPairDetailResponseMapper
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.TradeResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.model.TradingPairDetailResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.service.TradesWebSocketService
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.data.service.TradingPairWebSocketService
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.Trade
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.TradingPairDetail
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.mapper.TradingPairResponseMapper
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.data.model.TickerResponse.TradingPairsResponse
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model.TradingPair
import io.reactivex.Observable
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TradingPairDetailsRepositoryImplTest {

    @Mock
    private lateinit var tradingPairWebSocketService: TradingPairWebSocketService

    @Mock
    private lateinit var tradesWebSocketService: TradesWebSocketService

    @Mock
    private lateinit var tradingPairDetailResponseMapper: TradingPairDetailResponseMapper

    @Mock
    private lateinit var tradingPairResponseMapper: TradingPairResponseMapper

    @Mock
    private lateinit var tradeStore: TradesStore

    @Mock
    private lateinit var tradeResponseMapper: TradeResponseMapper

    @Mock
    private lateinit var tradingPairListInMemoryCache: TradingPairListInMemoryCache

    @InjectMocks
    private lateinit var repository: TradingPairDetailsRepositoryImpl

    @Test
    fun `should return a stream of trading pair details from web socket service`() {
        val symbol = "TEST"

        val tradingPairDetailResponse1 = Mockito.mock(TradingPairDetailResponse::class.java)
        val tradingPairDetailResponse2 = Mockito.mock(TradingPairDetailResponse::class.java)

        val tradingPairDetail1 = Mockito.mock(TradingPairDetail::class.java)
        val tradingPairDetail2 = Mockito.mock(TradingPairDetail::class.java)

        whenever(tradingPairWebSocketService.observeTradingPairChanges(symbol)).thenReturn(
            Observable.just(tradingPairDetailResponse1, tradingPairDetailResponse2)
        )

        whenever(tradingPairDetailResponseMapper.map(tradingPairDetailResponse1))
            .thenReturn(tradingPairDetail1)

        whenever(tradingPairDetailResponseMapper.map(tradingPairDetailResponse2))
            .thenReturn(tradingPairDetail2)

        repository.getTradingPairDetails(symbol)
            .test()
            .assertNoErrors()
            .assertValueCount(2)
            .assertValueAt(0, tradingPairDetail1)
            .assertValueAt(1, tradingPairDetail2)

        verify(tradingPairWebSocketService).observeTradingPairChanges(symbol)
        verify(tradingPairDetailResponseMapper).map(tradingPairDetailResponse1)
        verify(tradingPairDetailResponseMapper).map(tradingPairDetailResponse2)
    }

    @Test
    fun `should return a stream of trades from web socket service`() {
        val symbol = "TEST"

        val tradeResponse1 = Mockito.mock(TradeResponse::class.java)
        val tradeResponse2 = Mockito.mock(TradeResponse::class.java)

        val trade1 = Mockito.mock(Trade::class.java)
        val trade2 = Mockito.mock(Trade::class.java)

        whenever(tradesWebSocketService.observeTradeChanges(symbol))
            .thenReturn(Observable.just(tradeResponse1, tradeResponse2))

        whenever(tradeStore.getAllTrades())
            .thenReturn(listOf(tradeResponse1))
            .thenReturn(listOf(tradeResponse1, tradeResponse2))

        whenever(tradeResponseMapper.mapList(listOf(tradeResponse1)))
            .thenReturn(listOf(trade1))

        whenever(tradeResponseMapper.mapList(listOf(tradeResponse1, tradeResponse2)))
            .thenReturn(listOf(trade1, trade2))

        repository.getTopTrades(symbol)
            .test()
            .assertNoErrors()
            .assertValueCount(2)
            .assertValueAt(0, listOf(trade1))
            .assertValueAt(1, listOf(trade1, trade2))

        verify(tradeStore).clear()
        verify(tradesWebSocketService).observeTradeChanges(symbol)
        verify(tradeStore).addTrade(tradeResponse1)
        verify(tradeStore).addTrade(tradeResponse2)
        verify(tradeStore, times(2)).getAllTrades()
        verify(tradeResponseMapper).mapList(listOf(tradeResponse1))
        verify(tradeResponseMapper).mapList(listOf(tradeResponse1, tradeResponse2))
    }

    @Test
    fun `should return all cached trading pairs`() {
        val tradingPairResponseList = listOf(
            Mockito.mock(TradingPairsResponse::class.java),
            Mockito.mock(TradingPairsResponse::class.java),
            Mockito.mock(TradingPairsResponse::class.java)
        )

        val tradingPairList = listOf(
            Mockito.mock(TradingPair::class.java),
            Mockito.mock(TradingPair::class.java),
            Mockito.mock(TradingPair::class.java)
        )

        whenever(tradingPairListInMemoryCache.getCachedTradingPairList())
            .thenReturn(tradingPairResponseList)

        whenever(tradingPairResponseMapper.mapList(tradingPairResponseList))
            .thenReturn(tradingPairList)

        repository.gatAllTradingPairs()
            .test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValueAt(0, tradingPairList)

        verify(tradingPairListInMemoryCache).getCachedTradingPairList()
        verify(tradingPairResponseMapper).mapList(tradingPairResponseList)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(
            tradingPairWebSocketService,
            tradesWebSocketService,
            tradingPairDetailResponseMapper,
            tradingPairResponseMapper,
            tradeStore,
            tradeResponseMapper,
            tradingPairListInMemoryCache
        )
    }
}
