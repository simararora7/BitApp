package com.simararora.bitapp.features.tradingpairs.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.simararora.bitapp.common.SchedulersProvider
import com.simararora.bitapp.common.SingleDisposable
import com.simararora.bitapp.common.ViewState
import com.simararora.bitapp.common.ViewState.*
import com.simararora.bitapp.features.tradingpairs.di.TradingPairsScope
import com.simararora.bitapp.features.tradingpairs.domain.usecase.GetTradingPairs
import com.simararora.bitapp.features.tradingpairs.presentation.mapper.TradingPairViewModelMapper
import com.simararora.bitapp.features.tradingpairs.presentation.model.TradingPairUIModel
import javax.inject.Inject

@TradingPairsScope
class TradingPairsViewModel @Inject constructor(
    private val getTradingPairs: GetTradingPairs,
    private val schedulersProvider: SchedulersProvider,
    private val tradingPairViewModelMapper: TradingPairViewModelMapper
) : ViewModel() {

    private var getTradingPairsDisposable by SingleDisposable()

    private var tradingPairStateChangesLiveData: MutableLiveData<ViewState<List<TradingPairUIModel>>>? =
        null

    fun tradingPairStateChanges(): LiveData<ViewState<List<TradingPairUIModel>>> {
        if (tradingPairStateChangesLiveData == null) {
            tradingPairStateChangesLiveData = MutableLiveData()
            getTradingPairs()
        }
        return tradingPairStateChangesLiveData!!
    }

    private fun getTradingPairs() {
        getTradingPairsDisposable = getTradingPairs.execute("ALL")
            .subscribeOn(schedulersProvider.io)
            .map(tradingPairViewModelMapper::mapList)
            .map<ViewState<List<TradingPairUIModel>>> { Success(it) }
            .toObservable()
            .startWith(Loading())
            .onErrorReturn { Error(it) }
            .subscribe { viewState ->
                tradingPairStateChangesLiveData?.postValue(viewState)
            }

    }
}
