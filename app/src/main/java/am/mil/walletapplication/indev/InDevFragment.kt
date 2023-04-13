package am.mil.walletapplication.indev

import am.mil.walletapplication.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class InDevFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_in_dev, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = InDevFragment()
    }
}