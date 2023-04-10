package am.mil.presentationapp.account

import am.mil.presentationapp.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class AccountMainTabFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account_main_tab, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AccountMainTabFragment()
    }
}