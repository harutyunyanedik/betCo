package am.mil.presentationapp.base.fragment

import am.mil.presentationapp.WalletApplication.Companion.networkStateLiveData
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseWalletFragmentFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData() {
        networkStateLiveData.observe(viewLifecycleOwner) {
            hideSwipeRefreshLayoutAction()
        }
    }

    open fun hideSwipeRefreshLayoutAction() {}

    open fun onStateInVisible() {}

    open fun onStateVisible() {}
}