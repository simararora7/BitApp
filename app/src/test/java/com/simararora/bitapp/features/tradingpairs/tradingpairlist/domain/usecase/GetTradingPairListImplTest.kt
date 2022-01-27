package com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.usecase

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model.TradingPair
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.repository.TradingPairListRepository
import io.reactivex.Single
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetTradingPairListImplTest {

    @Mock
    private lateinit var repository: TradingPairListRepository

    @InjectMocks
    private lateinit var usecase: GetTradingPairListImpl

    @Test
    fun `should get list of trading pairs from repository`() {
        val symbol = "TEST"

        val tradingPairs = listOf(
            Mockito.mock(TradingPair::class.java),
            Mockito.mock(TradingPair::class.java),
            Mockito.mock(TradingPair::class.java)
        )

        whenever(repository.getTradingPairList(symbol))
            .thenReturn(Single.just(tradingPairs))

        usecase.getTradingPairList(symbol)
            .test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValueAt(0, tradingPairs)

        verify(repository).getTradingPairList(symbol)
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(repository)
    }
}
