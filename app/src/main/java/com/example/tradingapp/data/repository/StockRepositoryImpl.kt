package com.example.tradingapp.data.repository

import com.example.tradingapp.data.mapper.toDomain
import com.example.tradingapp.data.remote.api.MarketApiService
import com.example.tradingapp.domain.model.Stock
import com.example.tradingapp.domain.repository.StockRepository
import com.example.tradingapp.utils.Result

class StockRepositoryImpl(
    private val api: MarketApiService
) : StockRepository {

    override suspend fun getStockPrices(
        ticker: String,
        from: String,
        to: String
    ): Result<List<Stock>> {
        return try {
            val response = api.getAggregates(ticker, from, to)
            val stocks = response.results?.map { it.toDomain(ticker) } ?: emptyList()
            Result.Success(stocks)
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknown error")
        }
    }
}
