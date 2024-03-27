package com.jerryalberto.mmas.feature.home.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jerryalberto.mmas.core.designsystem.theme.MmasTheme
import com.jerryalberto.mmas.core.model.data.TransactionType
import com.jerryalberto.mmas.feature.home.ui.screen.InputScreen
import com.jerryalberto.mmas.feature.home.ui.viewmodel.InputScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class InputActivity : ComponentActivity() {

    companion object {
        val PARAM_TYPE: String = "TYPE"
    }

    private val viewModel: InputScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getStringExtra(PARAM_TYPE)?.let {typeString->
            val transactionType = TransactionType.entries.find { it.value == typeString}
            transactionType?.let {
                viewModel.setTransactionType(it)
            } ?: viewModel.setTransactionType(TransactionType.INCOME)
        }

        setContent {
            MmasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    InputScreen(
                        viewModel = viewModel,
                        onTopBarLeftClick = {
                            this.finish()
                        }
                    )
                }

            }
        }

        observeViewModelEvents()
    }

    private fun observeViewModelEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.onSaved.collectLatest {
                    Timber.d("observe::${it}")
                    it?.let {
                        finish()
                    }
                }
            }
        }
    }
}