package com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.simararora.bitapp.common.SchedulersProvider
import com.simararora.bitapp.common.ViewState
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.model.TradingPair
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.usecase.GetTradingPairs
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.TradingPairsAction.InitialLoadAction
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.mapper.TradingPairViewModelMapper
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.model.TradingPairUIModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TradingPairListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getTradingPairs: GetTradingPairs

    @Mock
    private lateinit var schedulersProvider: SchedulersProvider

    @Mock
    private lateinit var tradingPairViewModelMapper: TradingPairViewModelMapper

    @Mock
    private lateinit var tradingPairListNavigator: TradingPairListNavigator

    @Mock
    private lateinit var mockViewStateObserver: Observer<ViewState<List<TradingPairUIModel>>>

    @InjectMocks
    private lateinit var viewModel: TradingPairListViewModel

    @Test
    fun `should dispatch loading and success view states with data if no error is received from domain while fetching trading pairs`() {
        viewModel.tradingPairStateChanges.observeForever(mockViewStateObserver)

        val domainModels = listOf(
            Mockito.mock(TradingPair::class.java),
            Mockito.mock(TradingPair::class.java),
            Mockito.mock(TradingPair::class.java)
        )

        val uiModels = listOf(
            Mockito.mock(TradingPairUIModel::class.java),
            Mockito.mock(TradingPairUIModel::class.java),
            Mockito.mock(TradingPairUIModel::class.java)
        )

        whenever(getTradingPairs.getTradingPairList("ALL"))
            .thenReturn(Single.just(domainModels))
        whenever(schedulersProvider.io)
            .thenReturn(Schedulers.trampoline())
        whenever(tradingPairViewModelMapper.mapList(domainModels))
            .thenReturn(uiModels)

        viewModel.handleAction(InitialLoadAction)

        verify(getTradingPairs).getTradingPairList("ALL")
        verify(schedulersProvider).io
        verify(tradingPairViewModelMapper).mapList(domainModels)
        verify(mockViewStateObserver).onChanged(ViewState.Loading())
        verify(mockViewStateObserver).onChanged(ViewState.Success(uiModels))
    }

    @Test
    fun `should dispatch loading and error view states if error is received from domain while fetching trading pairs`() {
        viewModel.tradingPairStateChanges.observeForever(mockViewStateObserver)

        val throwable = Throwable("Something Went Wrong!")

        whenever(getTradingPairs.getTradingPairList("ALL"))
            .thenReturn(Single.error(throwable))
        whenever(schedulersProvider.io)
            .thenReturn(Schedulers.trampoline())

        viewModel.handleAction(InitialLoadAction)

        verify(getTradingPairs).getTradingPairList("ALL")
        verify(schedulersProvider).io
        verify(mockViewStateObserver).onChanged(ViewState.Loading())
        verify(mockViewStateObserver).onChanged(ViewState.Error(throwable))
    }

    @Test
    fun `should navigate to trading pair details screen on receiving TradingPairsAction action`() {
        viewModel.handleAction(TradingPairsAction.TradingPairClickAction("TEST"))
        verify(tradingPairListNavigator).navigateToTradingPairDetailScreen("TEST")
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(
            getTradingPairs,
            schedulersProvider,
            tradingPairViewModelMapper,
            tradingPairListNavigator,
            mockViewStateObserver
        )
    }
}
