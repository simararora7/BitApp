package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.usecase

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.Trade
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.TradingPairDetail
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.repository.TradingPairDetailsRepository
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model.TradingPair
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TradingPairDetailUseCaseImplTest {

    @Mock
    private lateinit var repository: TradingPairDetailsRepository

    @InjectMocks
    private lateinit var usecase: TradingPairDetailUseCaseImpl

    @Test
    fun `should get stream of trading pair detail changes from repository`() {
        val symbol = "TEST"

        val tradingPairDetail1 = Mockito.mock(TradingPairDetail::class.java)
        val tradingPairDetail2 = Mockito.mock(TradingPairDetail::class.java)

        whenever(repository.getTradingPairDetails(symbol))
            .thenReturn(Observable.just(tradingPairDetail1, tradingPairDetail2))

        usecase.getTradingPairDetails(symbol)
            .test()
            .assertNoErrors()
            .assertValueCount(2)
            .assertValueAt(0, tradingPairDetail1)
            .assertValueAt(1, tradingPairDetail2)

        verify(repository).getTradingPairDetails(symbol)
    }

    @Test
    fun `should get top trades from repository and sort them by timestamp in descending order`() {
        val symbol = "TEST"

        val trade1 = Mockito.mock(Trade::class.java)
        whenever(trade1.timestamp).thenReturn(1L)
        val trade2 = Mockito.mock(Trade::class.java)
        whenever(trade2.timestamp).thenReturn(3L)
        val trade3 = Mockito.mock(Trade::class.java)
        whenever(trade3.timestamp).thenReturn(2L)

        whenever(repository.getTopTrades(symbol))
            .thenReturn(Observable.just(listOf(trade1, trade2, trade3)))

        usecase.getTopTrades(symbol)
            .test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValueAt(0, listOf(trade2, trade3, trade1))

        verify(repository).getTopTrades(symbol)
    }

    @Test
    fun `should get all trading pairs from repository`() {
        val tradingPairs = listOf(
            Mockito.mock(TradingPair::class.java),
            Mockito.mock(TradingPair::class.java),
            Mockito.mock(TradingPair::class.java)
        )

        whenever(repository.gatAllTradingPairs())
            .thenReturn(Single.just(tradingPairs))

        usecase.gatAllTradingPairs()
            .test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValueAt(0, tradingPairs)

        verify(repository).gatAllTradingPairs()
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(repository)
    }
}
