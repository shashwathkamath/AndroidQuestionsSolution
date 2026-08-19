package com.kamath.fidelityandroidquestion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kamath.fidelityandroidquestion.math.MathOperation
import com.kamath.fidelityandroidquestion.math.MyViewModel
import com.kamath.fidelityandroidquestion.ui.theme.FidelityAndroidQuestionTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FidelityAndroidQuestionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val uiStateAverageResult by viewModel.uiStateAverage.collectAsStateWithLifecycle()
                    val uiStateSumResult by viewModel.uiStateSum.collectAsStateWithLifecycle()
                    val uiStateEvenNumbersResult by viewModel.uiStateEvenNumbers.collectAsStateWithLifecycle()

                    MathOperationScreen(
                        modifier = Modifier.padding(innerPadding),
                        uiStateAverageResult,
                        uiStateSumResult,
                        uiStateEvenNumbersResult
                    )
                }
            }
        }
    }
}


@Composable
fun MathOperationScreen(
    modifier: Modifier,
    uiStateAverageResult: MathOperation,
    uiStateSumResult: MathOperation,
    uiStateEvenNumbersResult: MathOperation
) {
    Column {
        Button(onClick = { }) { Text("Calculate") }
        UiStateAverageResultText(uiStateAverageResult)
        UiStateSumResultText(uiStateSumResult)
        UiStateEvenNumbersResultText(uiStateEvenNumbersResult)
    }
}

@Composable
fun UiStateAverageResultText(uiStateAverageResult: MathOperation) {
    when (uiStateAverageResult) {
        is MathOperation.Loading -> Text("Loading...")
        is MathOperation.AverageResult -> Text("Average : ${uiStateAverageResult.result}")
        else -> Unit
    }
}

@Composable
fun UiStateSumResultText(uiStateSumResult: MathOperation) {
    when (uiStateSumResult) {
        is MathOperation.Loading -> Text("Loading...")
        is MathOperation.SumResult -> Text("Average : ${uiStateSumResult.result}")
        else -> Unit
    }
}

@Composable
fun UiStateEvenNumbersResultText(uiStateEvenNumbersResult: MathOperation) {
    when (uiStateEvenNumbersResult) {
        is MathOperation.Loading -> Text("Loading...")
        is MathOperation.EvenNumbersResult -> Text("Average : ${uiStateEvenNumbersResult.result}")
        else -> Unit
    }
}


