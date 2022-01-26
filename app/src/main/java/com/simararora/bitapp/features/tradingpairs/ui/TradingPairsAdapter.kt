package com.simararora.bitapp.features.tradingpairs.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simararora.bitapp.common.extensions.setTextStyle
import com.simararora.bitapp.databinding.ItemTradingPairBinding
import com.simararora.bitapp.features.tradingpairs.presentation.model.TradingPairUIModel

class TradingPairsAdapter : RecyclerView.Adapter<TradingPairsAdapter.TradingPairViewHolder>() {

    private var tradingPairs: List<TradingPairUIModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradingPairViewHolder {
        return TradingPairViewHolder(
            ItemTradingPairBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TradingPairViewHolder, position: Int) {
        val tradingPair = tradingPairs[position]
        with(holder.binding) {
            tvSymbol.text = tradingPair.symbol
            tvLastDayPrice.text = tradingPair.lastPrice
            tvDailyChange.text = tradingPair.dailyChangeRelative
            tvLastDayPrice.setTextStyle(tradingPair.bodyTextStyle)
            tvDailyChange.setTextStyle(tradingPair.bodyTextStyle)
        }
    }

    override fun getItemCount() = tradingPairs.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateTradingPairs(tradingPairs: List<TradingPairUIModel>) {
        this.tradingPairs = tradingPairs
        notifyDataSetChanged()
    }

    class TradingPairViewHolder(
        val binding: ItemTradingPairBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
