package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.simararora.bitapp.common.SchedulersProvider
import com.simararora.bitapp.common.ViewState
import com.simararora.bitapp.common.extensions.exhaustive
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.di.TradingPairDetailScope
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.usecase.TradingPairDetailUseCase
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.TradingPairDetailAction.LoadTradingPairDetails
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.mapper.TradeUIModelMapper
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.mapper.TradingPairDetailItemUiModelMapper
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.model.TradeUiModel
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.model.TradingPairDetailItemUiModel
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.mapper.TradingPairViewModelMapper
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.model.TradingPairUIModel
import io.reactivex.disposables.Disposable
import javax.inject.Inject


@TradingPairDetailScope
class TradingPairDetailViewModel @Inject constructor(
    private val tradingPairDetailUseCase: TradingPairDetailUseCase,
    private val schedulersProvider: SchedulersProvider,
    private val tradingPairDetailItemUiModelMapper: TradingPairDetailItemUiModelMapper,
    private val tradingPairViewModelMapper: TradingPairViewModelMapper,
    private val tradeUIModelMapper: TradeUIModelMapper
) : ViewModel() {

    @VisibleForTesting
    internal lateinit var currentSelectedSymbol: String

    // Trading Pair List Stream
    private var tradingPairListDisposable: Disposable? = null
    private val tradingPairListLiveData = MutableLiveData<List<TradingPairUIModel>>()
    val tradingPairListChanges: LiveData<List<TradingPairUIModel>>
        get() = tradingPairListLiveData

    // Trading Pair Details Stream
    private var tradingPairDetailsDisposable: Disposable? = null
    private val tradingPairDetailLiveData =
        MutableLiveData<ViewState<List<TradingPairDetailItemUiModel>>>()
    val tradingPairDetailChanges: LiveData<ViewState<List<TradingPairDetailItemUiModel>>>
        get() = tradingPairDetailLiveData

    // Trades Stream
    private var tradesDisposable: Disposable? = null
    private val tradesLiveData = MutableLiveData<ViewState<List<TradeUiModel>>>()
    val tradesChanges: LiveData<ViewState<List<TradeUiModel>>>
        get() = tradesLiveData

    fun handleAction(action: TradingPairDetailAction) {
        when(action) {
            is LoadTradingPairDetails -> updateTradingPair(action.symbol)
        }.exhaustive()
    }

    private fun updateTradingPair(symbol: String) {
        this.currentSelectedSymbol = symbol
        observeTradingPairListChanges()
        observeTradeChanges()
        observeTradingPairDetailChanges()
    }

    @VisibleForTesting
    internal fun observeTradingPairListChanges() {
        tradingPairListDisposable?.dispose()
        tradingPairListDisposable = tradingPairDetailUseCase.gatAllTradingPairs()
            .subscribeOn(schedulersProvider.io)
            .map(tradingPairViewModelMapper::mapList)
            .map(::markCurrentSymbolAsSelected)
            .subscribe { tradingPairList ->
                tradingPairListLiveData.postValue(tradingPairList)
            }
    }

    @VisibleForTesting
    internal fun observeTradingPairDetailChanges() {
        tradingPairDetailsDisposable?.dispose()
        tradingPairDetailsDisposable =
            tradingPairDetailUseCase.getTradingPairDetails(currentSelectedSymbol)
                .subscribeOn(schedulersProvider.io)
                .map(tradingPairDetailItemUiModelMapper::map)
                .map<ViewState<List<TradingPairDetailItemUiModel>>> { ViewState.Success(it) }
                .startWith(ViewState.Loading())
                .onErrorReturn {
                    ViewState.Error(it)
                }.subscribe {
                    tradingPairDetailLiveData.postValue(it)
                }
    }

    @VisibleForTesting
    internal fun observeTradeChanges() {
        tradesDisposable?.dispose()
        tradesDisposable = tradingPairDetailUseCase.getTopTrades(currentSelectedSymbol)
            .subscribeOn(schedulersProvider.io)
            .map(tradeUIModelMapper::mapList)
            .map<ViewState<List<TradeUiModel>>> { ViewState.Success(it) }
            .startWith (ViewState.Loading())
            .onErrorReturn {
                ViewState.Error(it)
            }.subscribe {
                tradesLiveData.postValue(it)
            }
    }

    private fun markCurrentSymbolAsSelected(tradingPairList: List<TradingPairUIModel>): List<TradingPairUIModel> {
        tradingPairList.find { tradingPair ->
            tradingPair.symbol == currentSelectedSymbol
        }?.isSelected = true
        return tradingPairList
    }

    override fun onCleared() {
        super.onCleared()
        tradingPairListDisposable?.dispose()
        tradingPairDetailsDisposable?.dispose()
        tradesDisposable?.dispose()
    }
}
