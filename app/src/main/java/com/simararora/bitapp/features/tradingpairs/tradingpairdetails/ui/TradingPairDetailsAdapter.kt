package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.simararora.bitapp.common.extensions.setTextStyle
import com.simararora.bitapp.databinding.ItemTradingPairDetailBinding
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.model.TradingPairDetailItemUiModel
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.ui.TradingPairDetailsAdapter.TradingPairDetailsViewHolder

class TradingPairDetailsAdapter : RecyclerView.Adapter<TradingPairDetailsViewHolder>() {

    private var tradingPairDetailItemList: List<TradingPairDetailItemUiModel> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TradingPairDetailsViewHolder {
        return TradingPairDetailsViewHolder(
            ItemTradingPairDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TradingPairDetailsViewHolder, position: Int) {
        val tradingPairDetailItem = tradingPairDetailItemList[position]
        with(holder.binding) {
            tvLabel.setText(tradingPairDetailItem.labelRes)
            tvValue.text = tradingPairDetailItem.value
            tvValue.setTextStyle(tradingPairDetailItem.valueTextStyle)
        }
    }

    override fun getItemCount() = tradingPairDetailItemList.size

    fun updateTradingPairDetailItems(
        tradingPairDetailItemList: List<TradingPairDetailItemUiModel>
    ) {
        val oldTradingPairDetailItemList = this.tradingPairDetailItemList
        this.tradingPairDetailItemList = tradingPairDetailItemList
        DiffUtil.calculateDiff(
            TradingPairDetailsDiffUtil(
                oldTradingPairDetailItemList,
                this.tradingPairDetailItemList
            )
        ).dispatchUpdatesTo(this)
    }

    class TradingPairDetailsViewHolder(
        val binding: ItemTradingPairDetailBinding
    ) : RecyclerView.ViewHolder(binding.root)

    class TradingPairDetailsDiffUtil(
        private val oldItems: List<TradingPairDetailItemUiModel>,
        private val newItems: List<TradingPairDetailItemUiModel>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldItems.size
        }

        override fun getNewListSize(): Int {
            return newItems.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].labelRes == newItems[newItemPosition].labelRes
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }
    }
}
