package com.simararora.bitapp.features.tradingpairs.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.simararora.bitapp.common.ViewModelFactory
import com.simararora.bitapp.common.ViewState
import com.simararora.bitapp.common.extensions.exhaustive
import com.simararora.bitapp.common.extensions.hide
import com.simararora.bitapp.common.extensions.show
import com.simararora.bitapp.databinding.FragmentTradingPairsBinding
import com.simararora.bitapp.features.tradingpairs.di.TradingPairsComponent
import com.simararora.bitapp.features.tradingpairs.presentation.TradingPairsViewModel
import com.simararora.bitapp.features.tradingpairs.presentation.model.TradingPairUIModel
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

class TradingPairsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<TradingPairsViewModel>

    private val viewModel: TradingPairsViewModel by lazy(NONE) {
        viewModelFactory.get<TradingPairsViewModel>(requireActivity())
    }

    private lateinit var binding: FragmentTradingPairsBinding

    private val adapter: TradingPairsAdapter by lazy(NONE) {
        TradingPairsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TradingPairsComponent.build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTradingPairsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTradingPairs.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@TradingPairsFragment.adapter
        }

        viewModel.tradingPairStateChanges().observe(viewLifecycleOwner, ::renderViewState)
    }

    private fun renderViewState(viewState: ViewState<List<TradingPairUIModel>>) {
        when (viewState) {
            is ViewState.Loading -> binding.pbLoader.show()
            is ViewState.Success -> {
                binding.pbLoader.hide()
                adapter.updateTradingPairs(viewState.data)
            }
            is ViewState.Error -> {
            }
        }.exhaustive()
    }

    companion object {
        fun newInstance() = TradingPairsFragment()
    }
}
