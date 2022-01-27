package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Spinner
import com.simararora.bitapp.databinding.ItemTradingPairDropdownBinding
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.model.TradingPairUIModel

class TradingPairSpinnerAdapter(
    private val spinner: Spinner
) : BaseAdapter() {

    private var tradingPairList: List<TradingPairUIModel> = emptyList()

    override fun getCount(): Int {
        return tradingPairList.size
    }

    override fun getItem(position: Int): Any {
        return tradingPairList[position]
    }

    override fun getItemId(position: Int): Long {
        return tradingPairList[position].symbol.hashCode().toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder: TradingPairSpinnerViewHolder = if (convertView == null) {
            val binding =
                ItemTradingPairDropdownBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            TradingPairSpinnerViewHolder(binding).also {
                binding.root.tag = it
            }
        } else {
            convertView.tag as TradingPairSpinnerViewHolder
        }
        viewHolder.binding.tvSymbol.text = tradingPairList[position].formattedSymbol
        return viewHolder.binding.root
    }

    fun updateItems(tradingPairList: List<TradingPairUIModel>) {
        this.tradingPairList = tradingPairList
        notifyDataSetChanged()
        val currentSelectedPosition = tradingPairList.indexOfFirst { it.isSelected }
        spinner.setSelection(currentSelectedPosition)
    }

    class TradingPairSpinnerViewHolder(val binding: ItemTradingPairDropdownBinding)
}
