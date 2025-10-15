package com.example.tradingapp.presentation.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tradingapp.domain.model.Stock
import com.example.tradingapp.domain.usecase.GetStockPricesUseCase
import com.example.tradingapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val getStockPricesUseCase: GetStockPricesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MarketState>(MarketState.Loading)
    val state = _state.asStateFlow()

    fun loadTicker(ticker: String) {
        viewModelScope.launch {
            _state.value = MarketState.Loading
            when (val result = getStockPricesUseCase(ticker, "2024-10-01", "2024-10-14")) {
                is Result.Success -> _state.value = MarketState.Success(result.data)
                is Result.Error -> _state.value = MarketState.Error(result.message)
            }
        }
    }
}

sealed class MarketState {
    object Loading : MarketState()
    data class Success(val stocks: List<Stock>) : MarketState()
    data class Error(val message: String) : MarketState()
}
