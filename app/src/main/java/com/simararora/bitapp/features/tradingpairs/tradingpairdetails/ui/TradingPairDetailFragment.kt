package com.simararora.bitapp.features.tradingpairs.tradingpairdetails.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.simararora.bitapp.common.ViewModelFactory
import com.simararora.bitapp.common.ViewState
import com.simararora.bitapp.common.extensions.hide
import com.simararora.bitapp.common.extensions.setOnItemSelectedListener
import com.simararora.bitapp.common.extensions.show
import com.simararora.bitapp.common.extensions.showSnackBar
import com.simararora.bitapp.databinding.FragmentTradingPairDetialBinding
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.di.TradingPairDetailComponent
import com.simararora.bitapp.features.tradingpairs.tradingpairdetails.presentation.TradingPairDetailAction.LoadTradingPairDetails
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
        viewModel.handleAction(LoadTradingPairDetails(getSymbolFromArgs()))
    }

    private fun initialiseTradingPairListSpinner() {
        binding.spinnerTradingPair.apply {
            adapter = tradingPairSpinnerAdapter
            setOnItemSelectedListener { tradingPair: TradingPairUIModel ->
                viewModel.handleAction(LoadTradingPairDetails(tradingPair.symbol))
            }
        }
        viewModel.tradingPairListChanges.observe(viewLifecycleOwner, ::updateTradingPairList)
    }

    private fun initialiseTradingPairDetailsView() {
        binding.rvTradingPairDetails.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = tradingPairDetailsAdapter
            isNestedScrollingEnabled = true
        }
        viewModel.tradingPairDetailChanges.observe(viewLifecycleOwner, ::updateTradingPairDetails)
    }

    private fun initialiseTradesView() {
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

    private fun updateTradingPairDetails(viewState: ViewState<List<TradingPairDetailItemUiModel>>) {
        when (viewState) {
            is ViewState.Loading -> {
                binding.pbDetails.show()
                binding.rvTradingPairDetails.hide()
            }
            is ViewState.Error -> {
                binding.pbDetails.hide()
                binding.rvTradingPairDetails.hide()
                binding.flDetails.showSnackBar(viewState.throwable.message ?: "")
            }
            is ViewState.Success -> {
                binding.pbDetails.hide()
                binding.rvTradingPairDetails.show()
                tradingPairDetailsAdapter.updateTradingPairDetailItems(viewState.data)
            }
        }
    }

    private fun updateTrades(viewState: ViewState<List<TradeUiModel>>) {
        when (viewState) {
            is ViewState.Loading -> {
                binding.pbTrades.show()
                binding.rvTrades.hide()
            }
            is ViewState.Error -> {
                binding.pbTrades.hide()
                binding.rvTrades.hide()
                binding.clTrades.showSnackBar(viewState.throwable.message ?: "")
            }
            is ViewState.Success -> {
                binding.pbTrades.hide()
                binding.rvTrades.show()
                tradesAdapter.updateTrades(viewState.data)
            }
        }
    }

    private fun getSymbolFromArgs() =
        arguments?.getString(TradingPairDetailActivity.KEY_SYMBOL) ?: ""

    companion object {
        fun newInstance(extras: Bundle) = TradingPairDetailFragment()
            .apply { arguments = extras }
    }
}
