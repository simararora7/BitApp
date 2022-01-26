package com.simararora.bitapp.features.tradingpairs.tradingpairlist.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simararora.bitapp.common.extensions.setTextStyle
import com.simararora.bitapp.databinding.ItemTradingPairBinding
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.TradingPairsAction
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.TradingPairsAction.TradingPairClickAction
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.model.TradingPairUIModel

class TradingPairListAdapter(
    private val dispatchAction: (TradingPairsAction) -> Unit
) : RecyclerView.Adapter<TradingPairListAdapter.TradingPairViewHolder>() {

    private var tradingPairs: List<TradingPairUIModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradingPairViewHolder {
        return TradingPairViewHolder(
            ItemTradingPairBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TradingPairViewHolder, position: Int) {
        val tradingPair = tradingPairs[position]
        with(holder.binding) {
            tvSymbol.text = tradingPair.formattedSymbol
            tvLastDayPrice.text = tradingPair.lastPrice
            tvDailyChange.text = tradingPair.dailyChangeRelative
            tvLastDayPrice.setTextStyle(tradingPair.bodyTextStyle)
            tvDailyChange.setTextStyle(tradingPair.bodyTextStyle)
            root.setOnClickListener {
                dispatchAction(TradingPairClickAction(tradingPair.symbol))
            }
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
