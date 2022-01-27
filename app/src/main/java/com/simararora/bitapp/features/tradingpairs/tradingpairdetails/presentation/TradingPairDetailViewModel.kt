package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.simararora.bitapp.common.SchedulersProvider
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.di.TradingPairDetailScope
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.domain.usecase.TradingPairDetailUseCase
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

    private lateinit var currentSelectedSymbol: String

    // Trading Pair List Stream
    private var tradingPairListDisposable: Disposable? = null
    private val tradingPairListLiveData = MutableLiveData<List<TradingPairUIModel>>()
    val tradingPairListChanges: LiveData<List<TradingPairUIModel>>
        get() = tradingPairListLiveData

    // Trading Pair Details Stream
    private var tradingPairDetailsDisposable: Disposable? = null
    private val tradingPairDetailLiveData = MutableLiveData<List<TradingPairDetailItemUiModel>>()
    val tradingPairDetailChanges: LiveData<List<TradingPairDetailItemUiModel>>
        get() = tradingPairDetailLiveData

    // Trades Stream
    private var tradesDisposable: Disposable? = null
    private val tradesLiveData = MutableLiveData<List<TradeUiModel>>()
    val tradesChanges: LiveData<List<TradeUiModel>>
        get() = tradesLiveData

    fun setTradingPair(symbol: String) {
        this.currentSelectedSymbol = symbol
        observeTradingPairListChanges()
        observeTradeChanges()
        observeTradingPairDetailChanges()
    }

    private fun observeTradingPairListChanges() {
        tradingPairListDisposable?.dispose()
        tradingPairListDisposable = tradingPairDetailUseCase.gatAllTradingPairs()
            .subscribeOn(schedulersProvider.io)
            .map(tradingPairViewModelMapper::mapList)
            .map(::markCurrentSymbolAsSelected)
            .subscribe { tradingPairList ->
                tradingPairListLiveData.postValue(tradingPairList)
            }
    }

    private fun observeTradingPairDetailChanges() {
        // Terminate Existing Stream for old trading pair(if-any)
        tradingPairDetailsDisposable?.dispose()
        tradingPairDetailsDisposable =
            tradingPairDetailUseCase.getTradingPairDetails(currentSelectedSymbol)
                .subscribeOn(schedulersProvider.io)
                .doOnSubscribe {
                    // Reset UI for old trading pair(if-any)
                    tradingPairDetailLiveData.postValue(emptyList())
                }.map(tradingPairDetailItemUiModelMapper::map)
                .subscribe {
                    tradingPairDetailLiveData.postValue(it)
                }
    }

    private fun observeTradeChanges() {
        // Terminate Existing Stream for old trading pair(if-any)
        tradesDisposable?.dispose()
        tradesDisposable = tradingPairDetailUseCase.getTopTrades(currentSelectedSymbol)
            .subscribeOn(schedulersProvider.io)
            .doOnSubscribe {
                // Reset UI for old trading pair(if-any)
                tradesLiveData.postValue(emptyList())
            }
            .map(tradeUIModelMapper::mapList)
            .subscribe {
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
