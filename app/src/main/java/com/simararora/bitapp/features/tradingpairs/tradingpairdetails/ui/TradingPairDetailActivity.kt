package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.simararora.bitapp.R
import com.simararora.bitapp.databinding.ActivityTradingPairDetailBinding

class TradingPairDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityTradingPairDetailBinding.inflate(layoutInflater).root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null && intent.extras != null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.flContainer, TradingPairDetailFragment.newInstance(intent.extras!!))
                .commitAllowingStateLoss()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
