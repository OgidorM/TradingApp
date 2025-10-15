package com.example.tradingapp.domain.model

data class Stock(
    val ticker: String,
    val open: Double,
    val close: Double,
    val high: Double,
    val low: Double,
    val volume: Double,
    val timestamp: Long
)
