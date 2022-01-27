package com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.simararora.bitapp.common.SchedulersProvider
import com.simararora.bitapp.common.SingleDisposable
import com.simararora.bitapp.common.ViewState
import com.simararora.bitapp.common.ViewState.*
import com.simararora.bitapp.common.extensions.exhaustive
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.di.TradingPairListScope
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.domain.usecase.GetTradingPairs
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.TradingPairsAction.InitialLoadAction
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.TradingPairsAction.TradingPairClickAction
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.mapper.TradingPairViewModelMapper
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.model.TradingPairUIModel
import javax.inject.Inject

@TradingPairListScope
class TradingPairListViewModel @Inject constructor(
    private val getTradingPairs: GetTradingPairs,
    private val schedulersProvider: SchedulersProvider,
    private val tradingPairViewModelMapper: TradingPairViewModelMapper,
    private val tradingPairListNavigator: TradingPairListNavigator
) : ViewModel() {

    private var getTradingPairsDisposable by SingleDisposable()

    private var tradingPairStateChangesLiveData: MutableLiveData<ViewState<List<TradingPairUIModel>>> =
        MutableLiveData()
    val tradingPairStateChanges: LiveData<ViewState<List<TradingPairUIModel>>>
        get() = tradingPairStateChangesLiveData

    fun handleAction(action: TradingPairsAction) {
        when (action) {
            is InitialLoadAction -> getTradingPairs()
            is TradingPairClickAction -> tradingPairListNavigator
                .navigateToTradingPairDetailScreen(action.symbol)
        }.exhaustive()
    }

    private fun getTradingPairs() {
        getTradingPairsDisposable = getTradingPairs.getTradingPairList("ALL")
            .subscribeOn(schedulersProvider.io)
            .map(tradingPairViewModelMapper::mapList)
            .map<ViewState<List<TradingPairUIModel>>> { Success(it) }
            .toObservable()
            .startWith(Loading())
            .onErrorReturn { Error(it) }
            .subscribe { viewState ->
                tradingPairStateChangesLiveData.postValue(viewState)
            }
    }

    override fun onCleared() {
        super.onCleared()
        getTradingPairsDisposable?.dispose()
    }
}
