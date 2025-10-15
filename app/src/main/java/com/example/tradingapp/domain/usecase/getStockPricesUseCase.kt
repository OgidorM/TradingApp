package com.example.tradingapp.domain.usecase

import com.example.tradingapp.domain.repository.StockRepository

class GetStockPricesUseCase(
    private val repository: StockRepository
) {
    suspend operator fun invoke(ticker: String, from: String, to: String) =
        repository.getStockPrices(ticker, from, to)
}
