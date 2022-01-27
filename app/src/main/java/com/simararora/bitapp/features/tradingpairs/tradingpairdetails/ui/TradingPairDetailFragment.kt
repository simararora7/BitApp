package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.simararora.bitapp.R
import com.simararora.bitapp.common.ViewModelFactory
import com.simararora.bitapp.common.extensions.setOnItemSelectedListener
import com.simararora.bitapp.databinding.FragmentTradingPairDetialBinding
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.di.TradingPairDetailComponent
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.TradingPairDetailViewModel
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.model.TradeUiModel
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.model.TradingPairDetailItemUiModel
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.model.TradingPairUIModel
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

class TradingPairDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<TradingPairDetailViewModel>

    private val viewModel: TradingPairDetailViewModel by lazy(NONE) {
        viewModelFactory.get<TradingPairDetailViewModel>(requireActivity())
    }

    private lateinit var binding: FragmentTradingPairDetialBinding

    private val tradingPairSpinnerAdapter: TradingPairSpinnerAdapter by lazy(NONE) {
        TradingPairSpinnerAdapter(binding.spinnerTradingPair)
    }

    private val tradingPairDetailsAdapter: TradingPairDetailsAdapter by lazy(NONE) {
        TradingPairDetailsAdapter()
    }

    private val tradesAdapter: TradesAdapter by lazy(NONE) {
        TradesAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TradingPairDetailComponent.build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTradingPairDetialBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Init Views
        initialiseTradingPairListSpinner()
        initialiseTradingPairDetailsView()
        initialiseTradesView()

        // Load Data
        viewModel.setTradingPair(getSymbolFromArgs())
    }

    private fun initialiseTradingPairListSpinner(){
        binding.spinnerTradingPair.apply {
            adapter = tradingPairSpinnerAdapter
            setOnItemSelectedListener { tradingPair: TradingPairUIModel ->
                viewModel.setTradingPair(tradingPair.symbol)
            }
        }
        viewModel.tradingPairListChanges.observe(viewLifecycleOwner, ::updateTradingPairList)
    }

    private fun initialiseTradingPairDetailsView(){
        binding.rvTradingPairDetails.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = tradingPairDetailsAdapter
            isNestedScrollingEnabled = true
        }
        viewModel.tradingPairDetailChanges.observe(viewLifecycleOwner, ::updateTradingPairDetails)
    }

    private fun initialiseTradesView(){
        binding.rvTrades.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tradesAdapter
            isNestedScrollingEnabled = true
        }
        viewModel.tradesChanges.observe(viewLifecycleOwner, ::updateTrades)
    }

    private fun updateTradingPairList(tradingPairList: List<TradingPairUIModel>) {
        tradingPairSpinnerAdapter.updateItems(tradingPairList)
    }

    private fun updateTradingPairDetails(details: List<TradingPairDetailItemUiModel>) {
        binding.pbDetails.isVisible = details.isEmpty()
        tradingPairDetailsAdapter.updateTradingPairDetailItems(details)
    }

    private fun updateTrades(tradeList: List<TradeUiModel>) {
        binding.pbTrades.isVisible = tradeList.isEmpty()
        tradesAdapter.updateTrades(tradeList)
    }

    private fun getSymbolFromArgs() =
        arguments?.getString(TradingPairDetailActivity.KEY_SYMBOL) ?: ""

    companion object {
        fun newInstance(extras: Bundle) = TradingPairDetailFragment()
            .apply { arguments = extras }
    }
}
