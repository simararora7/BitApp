package com.simararora.bitapp.features.tradingpairs.tradingpairlist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.simararora.bitapp.R
import com.simararora.bitapp.databinding.ActivityTradingPairsBinding

class TradingPairListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityTradingPairsBinding.inflate(layoutInflater).root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.flContainer, TradingPairListFragment.newInstance())
                .commitAllowingStateLoss()
        }
    }
}
