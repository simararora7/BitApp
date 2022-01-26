package com.simararora.bitapp.features.tradingpairs.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.simararora.bitapp.R
import com.simararora.bitapp.databinding.ActivityTradingPairsBinding

class TradingPairsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityTradingPairsBinding.inflate(layoutInflater).root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.flContainer, TradingPairsFragment.newInstance())
                .commitAllowingStateLoss()
        }
    }
}
