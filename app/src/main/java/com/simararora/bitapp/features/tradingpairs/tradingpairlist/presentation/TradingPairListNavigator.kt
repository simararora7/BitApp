package com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation

import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.ui.TradingPairDetailActivity
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.ui.TradingPairListFragment
import javax.inject.Inject

interface TradingPairListNavigator {
    fun navigateToTradingPairDetailScreen(symbol: String)
}

class TradingPairListNavigatorImpl @Inject constructor(
    private val tradingPairListFragment: TradingPairListFragment
) : TradingPairListNavigator {

    override fun navigateToTradingPairDetailScreen(symbol: String) {
        val intent =
            TradingPairDetailActivity.getLaunchIntent(tradingPairListFragment.requireContext(), symbol)
        tradingPairListFragment.startActivity(intent)
    }
}
