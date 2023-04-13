package am.mil.walletapplication.base.fragment

import am.mil.walletapplication.R
import am.mil.walletapplication.WalletApplication.Companion.networkStateLiveData
import am.mil.walletapplication.base.activity.BaseWalletActivity
import am.mil.walletapplication.base.utils.WalletDefaultDialogData
import am.mil.walletapplication.base.utils.unbindDrawables
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import java.util.Stack

abstract class BaseWalletFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dismissLoadingDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as BaseWalletActivity).setupUI(view)
        observeLiveData()
    }

    private fun observeLiveData() {
        networkStateLiveData.observe(viewLifecycleOwner) {
            if (it != true)
                hideSwipeRefreshLayoutAction()
        }
    }

    override fun onDestroy() {
        if (view != null && isUnbindViews()) {
            requireView().unbindDrawables()
        }
        BaseWalletActivity.isShowLoadingLiveData.value?.clear()
        BaseWalletActivity.isShowLoadingLiveData.value = Stack()

        BaseWalletActivity.isShowErrorMessageLiveData.value?.clear()
        BaseWalletActivity.isShowErrorMessageLiveData.value = Stack()

        BaseWalletActivity.isShowSuccessMessageLiveData.value?.clear()
        BaseWalletActivity.isShowSuccessMessageLiveData.value = Stack()

        BaseWalletActivity.isShowInfoMessageLiveData.value?.clear()
        BaseWalletActivity.isShowInfoMessageLiveData.value = Stack()

        BaseWalletActivity.isShowConfirmationMessageLiveData.value?.clear()
        BaseWalletActivity.isShowConfirmationMessageLiveData.value = Stack()
        super.onDestroy()
    }

    override fun onDestroyView() {
        if (view != null) {
            requireView().unbindDrawables()
        }

        dismissLoadingDialog()
        super.onDestroyView()
    }

    fun showLoadingDialog() {
        (requireActivity() as BaseWalletActivity).showLoadingDialog()
    }
    
    fun dismissLoadingDialog() {
        (requireActivity() as BaseWalletActivity).dismissLoadingDialog()
    }

    open fun isUnbindViews(): Boolean = true

    open fun hideSwipeRefreshLayoutAction() {}

    open fun onStateInVisible() {}

    open fun onStateVisible() {}
    
    companion object {
        fun addLoader() {
            BaseWalletActivity.addLoader()
        }

        fun removeLoader() {
            BaseWalletActivity.removeLoader()
        }

        fun showErrorMessageDialog(title: String, message: String, iconRes: Int = R.drawable.ic_success, isCancelable: Boolean = true) {
            BaseWalletActivity.showErrorMessageDialog(title, message, iconRes, isCancelable)
        }

        fun showErrorMessageDialog(errorMessageDialogData: WalletDefaultDialogData) {
            BaseWalletActivity.showErrorMessageDialog(errorMessageDialogData)
        }

        fun showSuccessMessageDialog(title: String, message: String, iconRes: Int = R.drawable.ic_success, isCancelable: Boolean = true) {
            BaseWalletActivity.showSuccessMessageDialog(title, message, iconRes, isCancelable)
        }

        fun showSuccessMessageDialog(successMessageDialogData: WalletDefaultDialogData) {
            BaseWalletActivity.showSuccessMessageDialog(successMessageDialogData)
        }

        fun showInfoMessageDialog(title: String, message: String, iconRes: Int = R.drawable.ic_info, isCancelable: Boolean = true) {
            BaseWalletActivity.showInfoMessageDialog(title, message, iconRes, isCancelable)
        }

        fun showInfoMessageDialog(successMessageDialogData: WalletDefaultDialogData) {
            BaseWalletActivity.showInfoMessageDialog(successMessageDialogData)
        }

        fun showConfirmationMessageDialog(title: String, message: String, iconRes: Int = R.drawable.ic_confirmation, isCancelable: Boolean = true, okClick: () -> Unit = {}, cancelClick: () -> Unit = {}) {
            BaseWalletActivity.showConfirmationMessageDialog(title, message, iconRes, isCancelable, okClick = okClick, cancelClick = cancelClick)
        }

        fun showConfirmationMessageDialog(successMessageDialogData: WalletDefaultDialogData) {
            BaseWalletActivity.showConfirmationMessageDialog(successMessageDialogData)
        }
    }
}