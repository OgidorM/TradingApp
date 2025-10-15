package com.example.tradingapp.data.remote.api

import com.example.tradingapp.BuildConfig
import com.example.tradingapp.data.remote.dto.StockAggregateResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarketApiService {

    @GET("/v2/aggs/ticker/{ticker}/range/1/day/{from}/{to}")
    suspend fun getAggregates(
        @Path("ticker") ticker: String,
        @Path("from") from: String,
        @Path("to") to: String,
        @Query("adjusted") adjusted: Boolean = true,
        @Query("sort") sort: String = "asc",
        @Query("limit") limit: Int = 50,
        @Query("apiKey") apiKey: String = BuildConfig.POLYGON_API_KEY
    ): StockAggregateResponse
}
