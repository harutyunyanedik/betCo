package am.mil.walletapplication.home

import am.mil.walletapplication.R
import am.mil.walletapplication.base.fragment.BaseWalletFragment
import am.mil.walletapplication.base.utils.WalletPreferencesManager
import am.mil.walletapplication.base.utils.viewLifecycle
import am.mil.walletapplication.category.CategoryAdapter
import am.mil.walletapplication.databinding.FragmentHomeMainTabBinding
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeMainTabFragment : BaseWalletFragment() {

    private var binding: FragmentHomeMainTabBinding by viewLifecycle()
    private val viewModel by viewModel<HomeMainTabViewModel>()
    private val categoryAdapter: CategoryAdapter = CategoryAdapter {
        if (!it.childMenuItems.isNullOrEmpty())
            findNavController().navigate(HomeMainTabFragmentDirections.actionGlobalCategoryFragment(it))
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
        binding = FragmentHomeMainTabBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            fragment = this@HomeMainTabFragment
        }
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
        binding.balanceTextView.transformationMethod = if (WalletPreferencesManager.isShowBalance()) null else PasswordTransformationMethod()
        val imageResId = if (WalletPreferencesManager.isShowBalance()) R.drawable.ic_password_hide else R.drawable.ic_password_show
        binding.showHidePasswordImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), imageResId))
        binding.promotionRecyclerView.adapter = promotionAdapter
    }

    private fun setupListeners() {
        binding.showHidePasswordImageView.setOnClickListener {
            binding.balanceTextView.transformationMethod = if (WalletPreferencesManager.isShowBalance()) PasswordTransformationMethod() else null
            val imageResId = if (WalletPreferencesManager.isShowBalance()) R.drawable.ic_password_show else R.drawable.ic_password_hide
            binding.showHidePasswordImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), imageResId))
            WalletPreferencesManager.putIsShowBalance(!WalletPreferencesManager.isShowBalance())
        }
    }

    private fun setupObservers() {
        viewModel.menuItemsLiveData.observe(viewLifecycleOwner) {
            removeLoader()
            categoryAdapter.updateData(it.toMutableList())
        }
    }
}