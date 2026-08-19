package com.kamath.fidelityandroidquestion

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kamath.fidelityandroidquestion.stockPrice.MyViewModel
import com.kamath.fidelityandroidquestion.stockPrice.StockPriceUiState
import com.kamath.fidelityandroidquestion.ui.theme.FidelityAndroidQuestionTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FidelityAndroidQuestionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                    val contenxt = LocalContext.current
                    LaunchedEffect(Unit) {
                        viewModel.toastEvents.collect { message ->
                            Toast.makeText(contenxt, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                    Greeting(
                        name = when (val state = uiState) {
                            is StockPriceUiState.Loading -> "Loading..."
                            is StockPriceUiState.Success -> "${state.stockPrice.symbol}: $%.2f".format(
                                state.stockPrice.price
                            )
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = name,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FidelityAndroidQuestionTheme {
        Greeting("Android")
    }
}