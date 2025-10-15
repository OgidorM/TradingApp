package com.example.tradingapp.domain.repository

import com.example.tradingapp.domain.model.Stock
import com.example.tradingapp.utils.Result

interface StockRepository {
    suspend fun getStockPrices(ticker: String, from: String, to: String): Result<List<Stock>>
}
