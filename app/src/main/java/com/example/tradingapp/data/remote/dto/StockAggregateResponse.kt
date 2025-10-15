package com.example.tradingapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StockAggregateResponse(
    val results: List<StockCandleDto>? = null,
    val ticker: String? = null,
    val queryCount: Int? = null,
    val resultsCount: Int? = null,
    val adjusted: Boolean? = null,
    val status: String? = null,
    @SerialName("request_id") val requestId: String? = null,
    val count: Int? = null
)

@Serializable
data class StockCandleDto(
    val o: Double,
    val h: Double,
    val l: Double,
    val c: Double,
    val v: Double,
    val vw: Double? = null,
    val t: Long,
    val n: Int? = null
)
