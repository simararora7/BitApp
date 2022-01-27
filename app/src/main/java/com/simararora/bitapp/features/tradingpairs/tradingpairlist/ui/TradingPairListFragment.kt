package com.simararora.bitapp.features.tradingpairs.tradingpairlist.ui

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
import com.simararora.bitapp.common.extensions.showSnackBar
import com.simararora.bitapp.databinding.FragmentTradingPairsBinding
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.di.TradingPairListComponent
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.TradingPairListViewModel
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.TradingPairsAction
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.TradingPairsAction.InitialLoadAction
import com.simararora.bitapp.features.tradingpairs.tradingpairlist.presentation.model.TradingPairUIModel
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE


class TradingPairListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<TradingPairListViewModel>

    private val viewModel: TradingPairListViewModel by lazy(NONE) {
        viewModelFactory.get<TradingPairListViewModel>(requireActivity())
    }

    private lateinit var binding: FragmentTradingPairsBinding

    private val adapter: TradingPairListAdapter by lazy(NONE) {
        TradingPairListAdapter(viewModel::handleAction)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TradingPairListComponent.build(this).inject(this)
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
            adapter = this@TradingPairListFragment.adapter
        }

        viewModel.tradingPairStateChanges.observe(viewLifecycleOwner, ::renderViewState)
        viewModel.handleAction(InitialLoadAction)
    }

    private fun renderViewState(viewState: ViewState<List<TradingPairUIModel>>) {
        when (viewState) {
            is ViewState.Loading -> binding.pbLoader.show()
            is ViewState.Success -> {
                binding.pbLoader.hide()
                adapter.updateTradingPairs(viewState.data)
            }
            is ViewState.Error -> {
                binding.pbLoader.hide()
                binding.root.showSnackBar(viewState.throwable.message ?: "")
            }
        }.exhaustive()
    }

    companion object {
        fun newInstance() = TradingPairListFragment()
    }
}
