package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simararora.bitapp.common.extensions.setTextStyle
import com.simararora.bitapp.databinding.ItemTradeBinding
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.model.TradeUiModel
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.ui.TradesAdapter.TradesViewHolder

class TradesAdapter : RecyclerView.Adapter<TradesViewHolder>() {

    private var trades: List<TradeUiModel> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TradesViewHolder {
        return TradesViewHolder(
            ItemTradeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TradesViewHolder, position: Int) {
        val trade = trades[position]
        with(holder.binding) {
            tvAmount.text = trade.amount
            tvPrice.text = trade.price
            tvTime.text = trade.displayTime
            tvPrice.setTextStyle(trade.priceTextStyle)
        }
    }

    override fun getItemCount() = trades.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateTrades(
        trades: List<TradeUiModel>
    ) {
        this.trades = trades
        notifyDataSetChanged()
    }

    class TradesViewHolder(
        val binding: ItemTradeBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
