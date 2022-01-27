package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation

sealed class TradingPairDetailAction {
    data class LoadTradingPairDetails(val symbol: String) : TradingPairDetailAction()
}
