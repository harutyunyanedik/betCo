package am.mil.presentationapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var doOperationButton: Button
    private lateinit var operationCompletedTextView: TextView
    private val viewModel: MainViewModel by lifecycleScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        observeLiveData()
    }

    private fun setupViews() {
        doOperationButton = findViewById(R.id.doOperation)
        operationCompletedTextView = findViewById(R.id.operationCompleted)
        doOperationButton.setOnClickListener {
            viewModel.doOperation()
        }
    }

    private fun observeLiveData() {
        viewModel.operationCompletedLiveData.observe(this) {
            operationCompletedTextView.isVisible = true
            operationCompletedTextView.text = it?.title
        }

        viewModel.operationErrorLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}