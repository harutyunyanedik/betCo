package am.mil.walletapplication.qrcode

import am.mil.walletapplication.base.utils.openQRScanner
import am.mil.walletapplication.base.utils.viewLifecycle
import am.mil.walletapplication.databinding.FragmentQrCodeMainTabBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.journeyapps.barcodescanner.ScanContract

class QrCodeMainTabFragment : Fragment() {

    private var binding: FragmentQrCodeMainTabBinding by viewLifecycle()
    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            Toast.makeText(requireContext(), "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "Scanned: " + result.contents, Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrCodeMainTabBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            fragment = this@QrCodeMainTabFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        openQRScanner("scan barcode", barcodeLauncher)
    }
}