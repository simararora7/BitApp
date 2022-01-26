package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.simararora.bitapp.R
import com.simararora.bitapp.databinding.ActivityTradingPairsBinding


class TradingPairDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityTradingPairsBinding.inflate(layoutInflater).root)

        if (savedInstanceState == null && intent.extras != null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.flContainer, TradingPairDetailFragment.newInstance(intent.extras!!))
                .commitAllowingStateLoss()
        }
    }

    companion object {

        const val KEY_SYMBOL = "symbol"

        fun getLaunchIntent(
            context: Context,
            symbol: String
        ) = Intent(context, TradingPairDetailActivity::class.java).apply {
            putExtra(KEY_SYMBOL, symbol)
        }
    }
}
