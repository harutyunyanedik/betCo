package am.mil.walletapplication.history

import am.mil.walletapplication.base.fragment.BaseWalletFragment
import am.mil.walletapplication.base.utils.viewLifecycle
import am.mil.walletapplication.databinding.FragmentTransactionHistoryDetailsBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class TransactionHistoryDetailsFragment : BaseWalletFragment() {

    private var binding: FragmentTransactionHistoryDetailsBinding by viewLifecycle()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentTransactionHistoryDetailsBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = viewLifecycleOwner
                fragment = this@TransactionHistoryDetailsFragment
            }
        return binding.root
    }

    companion object {

    }
}