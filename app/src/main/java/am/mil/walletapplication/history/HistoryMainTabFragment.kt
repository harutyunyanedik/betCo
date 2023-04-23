package am.mil.walletapplication.history

import am.mil.walletapplication.base.fragment.BaseWalletFragment
import am.mil.walletapplication.base.utils.viewLifecycle
import am.mil.walletapplication.databinding.FragmentHistoryMainTabBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryMainTabFragment : BaseWalletFragment() {

    private var binding: FragmentHistoryMainTabBinding by viewLifecycle()
    private val viewModel by viewModel<HistoryMainTabViewModel>()
    private val historyAdapter: HistoryPagingAdapter = HistoryPagingAdapter {
        findNavController().navigate(HistoryMainTabFragmentDirections.actionGlobalHistoryDetailsFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getHistories()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryMainTabBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            fragment = this@HistoryMainTabFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpViews() {
        binding.historyRecyclerView.adapter = historyAdapter
    }

    private fun setUpListeners() {

    }

    private fun setUpObservers() {
        viewModel.historyItemsLiveData.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                historyAdapter.submitData(it)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryMainTabFragment()
    }
}