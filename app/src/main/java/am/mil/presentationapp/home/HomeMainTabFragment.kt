package am.mil.presentationapp.home

import am.mil.presentationapp.R
import am.mil.presentationapp.base.fragment.BaseWalletFragmentFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class HomeMainTabFragment : BaseWalletFragmentFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_main_tab, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeMainTabFragment()
    }
}