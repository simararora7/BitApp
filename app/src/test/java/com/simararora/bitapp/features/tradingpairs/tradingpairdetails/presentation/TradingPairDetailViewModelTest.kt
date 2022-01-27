package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.simararora.bitapp.common.SchedulersProvider
import com.simararora.bitapp.common.ViewState
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.Trade
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.model.TradingPairDetail
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.usecase.TradingPairDetailUseCase
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.TradingPairDetailAction.LoadTradingPairDetails
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.mapper.TradeUIModelMapper
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.mapper.TradingPairDetailItemUiModelMapper
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.model.TradeUiModel
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.model.TradingPairDetailItemUiModel
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model.TradingPair
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.mapper.TradingPairViewModelMapper
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.model.TradingPairUIModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class TradingPairDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var tradingPairDetailUseCase: TradingPairDetailUseCase

    @Mock
    private lateinit var schedulersProvider: SchedulersProvider

    @Mock
    private lateinit var tradingPairDetailItemUiModelMapper: TradingPairDetailItemUiModelMapper

    @Mock
    private lateinit var tradingPairViewModelMapper: TradingPairViewModelMapper

    @Mock
    private lateinit var tradeUIModelMapper: TradeUIModelMapper

    @Mock
    private lateinit var tradingPairListObserver: Observer<List<TradingPairUIModel>>

    @Mock
    private lateinit var tradingPairDetailObserver: Observer<ViewState<List<TradingPairDetailItemUiModel>>>

    @Mock
    private lateinit var tradesObserver: Observer<ViewState<List<TradeUiModel>>>

    @Spy
    @InjectMocks
    private lateinit var viewModel: TradingPairDetailViewModel

    @Test
    fun `should handle LoadTradingPairDetails by observing to trading pair list, trading pair details and trade changes`() {
        doNothing().whenever(viewModel).observeTradingPairListChanges()
        doNothing().whenever(viewModel).observeTradeChanges()
        doNothing().whenever(viewModel).observeTradingPairDetailChanges()

        viewModel.handleAction(LoadTradingPairDetails("NEW"))
        assertEquals(viewModel.currentSelectedSymbol, "NEW")

        verify(viewModel).observeTradingPairListChanges()
        verify(viewModel).observeTradeChanges()
        verify(viewModel).observeTradingPairDetailChanges()
    }

    @Test
    fun `should get all trading pairs from domain and update isSelected of current selected symbol`() {
        viewModel.currentSelectedSymbol = "Symbol2"
        viewModel.tradingPairListChanges.observeForever(tradingPairListObserver)

        val tradingPairs = listOf(
            Mockito.mock(TradingPair::class.java),
            Mockito.mock(TradingPair::class.java)
        )

        val tradingPairUIModel1 = Mockito.mock(TradingPairUIModel::class.java)
        whenever(tradingPairUIModel1.symbol).thenReturn("Symbol1")
        val tradingPairUIModel2 = Mockito.mock(TradingPairUIModel::class.java)
        whenever(tradingPairUIModel2.symbol).thenReturn("Symbol2")

        whenever(tradingPairDetailUseCase.gatAllTradingPairs())
            .thenReturn(Single.just(tradingPairs))
        whenever(schedulersProvider.io).thenReturn(Schedulers.trampoline())
        whenever(tradingPairViewModelMapper.mapList(tradingPairs))
            .thenReturn(listOf(tradingPairUIModel1, tradingPairUIModel2))

        viewModel.observeTradingPairListChanges()

        verify(tradingPairDetailUseCase).gatAllTradingPairs()
        verify(schedulersProvider).io
        verify(tradingPairViewModelMapper).mapList(tradingPairs)
        verify(tradingPairListObserver).onChanged(listOf(tradingPairUIModel1, tradingPairUIModel2))
        verify(tradingPairUIModel1).symbol
        verify(tradingPairUIModel2).symbol
        verify(tradingPairUIModel2).isSelected = true
        verifyNoMoreInteractions(tradingPairUIModel1, tradingPairUIModel2)
    }

    @Test
    fun `should get trading details from domain and dispatch loading and success states`() {
        val symbol = "TEST"

        viewModel.tradingPairDetailChanges.observeForever(tradingPairDetailObserver)
        viewModel.currentSelectedSymbol = symbol

        val tradingPairDetail = Mockito.mock(TradingPairDetail::class.java)
        val tradingPairDetailItemUiModelList = listOf(
            Mockito.mock(TradingPairDetailItemUiModel::class.java),
            Mockito.mock(TradingPairDetailItemUiModel::class.java),
            Mockito.mock(TradingPairDetailItemUiModel::class.java)
        )

        whenever(tradingPairDetailUseCase.getTradingPairDetails(symbol))
            .thenReturn(Observable.just(tradingPairDetail))
        whenever(schedulersProvider.io).thenReturn(Schedulers.trampoline())
        whenever(tradingPairDetailItemUiModelMapper.map(tradingPairDetail))
            .thenReturn(tradingPairDetailItemUiModelList)

        viewModel.observeTradingPairDetailChanges()

        verify(tradingPairDetailUseCase).getTradingPairDetails(symbol)
        verify(schedulersProvider).io
        verify(tradingPairDetailItemUiModelMapper).map(tradingPairDetail)
        verify(tradingPairDetailObserver).onChanged(ViewState.Loading())
        verify(tradingPairDetailObserver).onChanged(
            ViewState.Success(
                tradingPairDetailItemUiModelList
            )
        )
    }

    @Test
    fun `should get trading details from domain and dispatch loading and error states in case of an error`() {
        val symbol = "TEST"

        viewModel.tradingPairDetailChanges.observeForever(tradingPairDetailObserver)
        viewModel.currentSelectedSymbol = symbol

        val throwable = Throwable()

        whenever(tradingPairDetailUseCase.getTradingPairDetails(symbol))
            .thenReturn(Observable.error(throwable))
        whenever(schedulersProvider.io).thenReturn(Schedulers.trampoline())

        viewModel.observeTradingPairDetailChanges()

        verify(tradingPairDetailUseCase).getTradingPairDetails(symbol)
        verify(schedulersProvider).io
        verify(tradingPairDetailObserver).onChanged(ViewState.Loading())
        verify(tradingPairDetailObserver).onChanged(ViewState.Error(throwable))
    }

    @Test
    fun `should get trades from domain and dispatch loading and success states`() {
        val symbol = "TEST"

        viewModel.tradesChanges.observeForever(tradesObserver)
        viewModel.currentSelectedSymbol = symbol

        val tradeList = listOf(
            Mockito.mock(Trade::class.java),
            Mockito.mock(Trade::class.java),
            Mockito.mock(Trade::class.java)
        )
        val tradeUiList = listOf(
            Mockito.mock(TradeUiModel::class.java),
            Mockito.mock(TradeUiModel::class.java),
            Mockito.mock(TradeUiModel::class.java)
        )

        whenever(tradingPairDetailUseCase.getTopTrades(symbol))
            .thenReturn(Observable.just(tradeList))
        whenever(schedulersProvider.io).thenReturn(Schedulers.trampoline())
        whenever(tradeUIModelMapper.mapList(tradeList)).thenReturn(tradeUiList)

        viewModel.observeTradeChanges()

        verify(tradingPairDetailUseCase).getTopTrades(symbol)
        verify(schedulersProvider).io
        verify(tradeUIModelMapper).mapList(tradeList)
        verify(tradesObserver).onChanged(ViewState.Loading())
        verify(tradesObserver).onChanged(ViewState.Success(tradeUiList))
    }

    @Test
    fun `should get trades from domain and dispatch loading and error states in case of an error`() {
        val symbol = "TEST"

        viewModel.tradesChanges.observeForever(tradesObserver)
        viewModel.currentSelectedSymbol = symbol

        val throwable = Throwable()

        whenever(tradingPairDetailUseCase.getTopTrades(symbol))
            .thenReturn(Observable.error(throwable))
        whenever(schedulersProvider.io).thenReturn(Schedulers.trampoline())

        viewModel.observeTradeChanges()

        verify(tradingPairDetailUseCase).getTopTrades(symbol)
        verify(schedulersProvider).io
        verify(tradesObserver).onChanged(ViewState.Loading())
        verify(tradesObserver).onChanged(ViewState.Error(throwable))
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(
            tradingPairDetailUseCase,
            schedulersProvider,
            tradingPairDetailItemUiModelMapper,
            tradingPairViewModelMapper,
            tradeUIModelMapper,
            tradesObserver,
            tradingPairDetailObserver
        )
    }
}
