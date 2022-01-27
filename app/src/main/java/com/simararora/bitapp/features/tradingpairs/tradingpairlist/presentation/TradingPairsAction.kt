package com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation

sealed class TradingPairsAction {
    object InitialLoadAction: TradingPairsAction()
    data class TradingPairClickAction(
        val symbol: String
    ) : TradingPairsAction()
}
