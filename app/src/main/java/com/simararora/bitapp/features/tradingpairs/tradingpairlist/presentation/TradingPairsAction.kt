package com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation

sealed class TradingPairsAction {
    data class TradingPairClickAction(
        val symbol: String
    ) : TradingPairsAction()
}
