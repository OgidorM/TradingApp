package com.example.tradingapp.data.mapper

import com.example.tradingapp.data.remote.dto.StockCandleDto
import com.example.tradingapp.domain.model.Stock

fun StockCandleDto.toDomain(ticker: String): Stock {
    return Stock(
        ticker = ticker,
        open = o,
        close = c,
        high = h,
        low = l,
        volume = v,
        timestamp = t
    )
}
