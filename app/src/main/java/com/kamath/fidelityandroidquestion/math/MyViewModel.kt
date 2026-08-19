package com.kamath.fidelityandroidquestion.math

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface MathOperation {
    data object Loading : MathOperation
    data class AverageResult(val result: Double) : MathOperation
    data class SumResult(val result: Double) : MathOperation
    data class EvenNumbersResult(val result: Int) : MathOperation
}

class MyViewModel : ViewModel() {
    val uiStateAverage: StateFlow<MathOperation> = calculateAverage(1, 100)
        .map<Double, MathOperation> { MathOperation.AverageResult(it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MathOperation.Loading
        )

    val uiStateSum: StateFlow<MathOperation> = calculateSum(1, 100)
        .map<Double, MathOperation> { MathOperation.SumResult(it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MathOperation.Loading
        )

    val uiStateEvenNumbers: StateFlow<MathOperation> = calculateEvenNumbers(1,100)
        .map<Int, MathOperation> { MathOperation.EvenNumbersResult(it) }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MathOperation.Loading
        )

    private fun calculateAverage(startValue: Int, endValue: Int) = flow {
        var sum = 0
        for (i in startValue..endValue) {
            sum += i
        }
        val avg = sum.toDouble() / (endValue - startValue + 1)
        emit(avg)
    }

    private fun calculateSum(startValue: Int, endValue: Int) = flow {
        var sum = 0
        for (i in startValue..endValue) {
            sum += i
        }
        emit(sum.toDouble())
    }

    private fun calculateEvenNumbers(startValue: Int, endValue: Int) = flow {
        var count = 0
        for (i in startValue..endValue) {
            if (i % 2 == 0) count++
        }
        emit(count)
    }
}