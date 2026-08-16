package com.kamath.fidelityandroidquestion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

data class StockPrice(val symbol: String, val price: Double)

sealed interface StockPriceUiState {
    data object Loading : StockPriceUiState
    data class Success(val stockPrice: StockPrice) : StockPriceUiState
}

class MyViewModel : ViewModel() {

    val uiState: StateFlow<StockPriceUiState> = googleStockPriceStream()
        .map<StockPrice, StockPriceUiState> { StockPriceUiState.Success(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StockPriceUiState.Loading
        )

    private val _toastEvents = MutableSharedFlow<String>(replay = 0, 1)
    val toastEvents: SharedFlow<String> = _toastEvents.asSharedFlow()

    init {
        viewModelScope.launch {
            var wasAbove: Boolean? = null
            uiState.collect { state ->
                if (state is StockPriceUiState.Success) {
                    val isAbove = state.stockPrice.price > 100.0
                    if (wasAbove != null && wasAbove != isAbove) {
                        val message = if (isAbove) {
                            "GOOGL crossed above $100!"
                        } else {
                            "GOOGL crossed below $100!"
                        }
                        _toastEvents.emit(message)
                    }
                    wasAbove = isAbove
                }
            }
        }
    }

    fun googleStockPriceStream(): Flow<StockPrice> = flow {
        var price = 100.0
        while (true) {
            delay(1000.milliseconds)
            val change = Random.nextDouble(-2.0, 2.0)
            price = (price + change).coerceAtLeast(1.0)
            emit(StockPrice("GOOGL", price))
        }
    }
}