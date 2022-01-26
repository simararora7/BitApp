package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.ui

import android.os.Bundle
import androidx.fragment.app.Fragment

class TradingPairDetailFragment : Fragment() {

    companion object {
        fun newInstance(extras: Bundle) = TradingPairDetailFragment()
            .apply {
                arguments = extras
            }
    }
}
