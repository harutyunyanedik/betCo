package am.mil.walletapplication.category

import am.mil.domain.category.model.CategoryItemViewTypeEnum.GRID
import am.mil.domain.category.model.CategoryItemViewTypeEnum.LINEAR
import am.mil.walletapplication.R
import am.mil.walletapplication.base.fragment.BaseWalletFragment
import am.mil.walletapplication.base.utils.WalletPreferencesManager
import am.mil.walletapplication.base.utils.viewLifecycle
import am.mil.walletapplication.databinding.FragmentCategoryBinding
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

class CategoryFragment : BaseWalletFragment() {

    private var binding: FragmentCategoryBinding by viewLifecycle()
    private val navArgs: CategoryFragmentArgs by navArgs()
    private val categoryAdapter: CategoryAdapter = CategoryAdapter {
        if (!it.childCategoryItems.isNullOrEmpty())
            findNavController().navigate(CategoryFragmentDirections.actionGlobalCategoryFragment(it))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCategoryBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            fragment = this@CategoryFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        binding.categoryRecyclerView.apply {
            layoutManager = if (WalletPreferencesManager.getCategoryItemViewType() == GRID) GridLayoutManager(requireContext(), GRID_SPAN_COUNT) else LinearLayoutManager(requireContext())
            adapter = categoryAdapter.apply { itemViewType = WalletPreferencesManager.getCategoryItemViewType() }
        }
        binding.balanceTextView.transformationMethod = if (WalletPreferencesManager.isShowBalance()) null else PasswordTransformationMethod()
        val imageResId = if (WalletPreferencesManager.isShowBalance()) R.drawable.ic_password_hide else R.drawable.ic_password_show
        binding.showHidePasswordImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), imageResId))
        val filterImageResId = if (WalletPreferencesManager.getCategoryItemViewType() == GRID) R.drawable.ic_list_vertical else R.drawable.ic_square_grid
        binding.filterImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), filterImageResId))
        categoryAdapter.updateData(navArgs.category.childCategoryItems?.toMutableList())
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupListeners() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.showHidePasswordImageView.setOnClickListener {
            binding.balanceTextView.transformationMethod = if (WalletPreferencesManager.isShowBalance()) PasswordTransformationMethod() else null
            val imageResId = if (WalletPreferencesManager.isShowBalance()) R.drawable.ic_password_show else R.drawable.ic_password_hide
            binding.showHidePasswordImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), imageResId))
            WalletPreferencesManager.putIsShowBalance(!WalletPreferencesManager.isShowBalance())
        }

        binding.filterImageView.setOnClickListener {
            val itemViewType = if (WalletPreferencesManager.getCategoryItemViewType() == GRID) LINEAR else GRID
            val layoutManager = if (WalletPreferencesManager.getCategoryItemViewType() == GRID) LinearLayoutManager(requireContext()) else GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
            WalletPreferencesManager.putCategoryItemViewType(itemViewType)
            categoryAdapter.itemViewType = itemViewType
            binding.categoryRecyclerView.layoutManager = layoutManager
            val filterImageResId = if (WalletPreferencesManager.getCategoryItemViewType() == GRID) R.drawable.ic_list_vertical else R.drawable.ic_square_grid
            binding.filterImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), filterImageResId))
            categoryAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        private const val GRID_SPAN_COUNT: Int = 3
    }
}