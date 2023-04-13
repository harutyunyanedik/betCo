package am.mil.walletapplication.home

import am.mil.walletapplication.base.fragment.BaseWalletFragment
import am.mil.walletapplication.base.utils.viewLifecycle
import am.mil.walletapplication.databinding.FragmentHomeMainTabBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeMainTabFragment : BaseWalletFragment() {

    private var binding: FragmentHomeMainTabBinding by viewLifecycle()
    private val viewModel by viewModel<HomeMainTabViewModel>()
    private val categoryAdapter: HomeCategoryAdapter = HomeCategoryAdapter {
        Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
    }
    private val promotionAdapter: HomePromotionAdapter = HomePromotionAdapter {
        Toast.makeText(requireContext(), "it", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addLoader()
        viewModel.getMenuItems()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeMainTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
        setupObservers()
    }

    private fun setupViews() {
        binding.recyclerView.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
        binding.promotionRecyclerView.adapter = promotionAdapter
    }

    private fun setupListeners() {
        binding.swipeToRefreshLayout.setOnRefreshListener {
            viewModel.getMenuItems()
        }
    }

    private fun setupObservers() {
        viewModel.menuItemsLiveData.observe(viewLifecycleOwner) {
            removeLoader()
            binding.swipeToRefreshLayout.isRefreshing = false
            categoryAdapter.updateData(it.toMutableList())
            val list = mutableListOf<String>()
            for (i in 0 until 7) {
                list.add("https://c8.alamy.com/comp/2M3TD5F/64-off-red-discount-banner-with-sixty-four-percent-advertising-for-mega-sale-promotion-stories-format-2M3TD5F.jpg")
            }
            promotionAdapter.updateData(list)
        }
    }

    override fun hideSwipeRefreshLayoutAction() {
        binding.swipeToRefreshLayout.isRefreshing = false
    }
}