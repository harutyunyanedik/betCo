package am.mil.walletapplication.history

import am.mil.walletapplication.base.fragment.BaseWalletFragment
import am.mil.walletapplication.base.utils.viewLifecycle
import am.mil.walletapplication.databinding.FragmentHistoryMainTabBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

class HistoryMainTabFragment : BaseWalletFragment() {

    private var binding: FragmentHistoryMainTabBinding by viewLifecycle()
//    private val navArgs: HistoryMainTabFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryMainTabBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            fragment = this@HistoryMainTabFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.backButton.setOnClickListener {

        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryMainTabFragment()
    }
}