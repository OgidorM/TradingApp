package com.example.tradingapp.presentation.marketdetails

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Mock stock data
data class StockDetails(
    val symbol: String,
    val name: String,
    val price: Double,
    val change: Double,
    val changePercent: Double,
    val marketCap: String,
    val volume: String,
    val high24h: Double,
    val low24h: Double,
    val pe: String,
    val yearHigh: Double,
    val yearLow: Double,
    val avgVolume: String,
    val description: String
)

// Generate fake detailed chart data
val detailedChartData = listOf(
    230.5f, 231.2f, 229.8f, 232.4f, 233.1f, 232.8f, 234.5f,
    235.2f, 234.8f, 236.1f, 237.5f, 236.9f, 238.2f, 239.1f,
    238.5f, 240.2f, 241.8f, 240.5f, 242.3f, 243.1f, 242.7f,
    244.5f, 245.2f, 244.8f, 246.1f, 247.0f, 246.5f, 248.2f,
    249.0f, 248.5f, 250.1f
)

val mockStock = StockDetails(
    symbol = "AAPL",
    name = "Apple Inc.",
    price = 250.15,
    change = 5.82,
    changePercent = 2.38,
    marketCap = "3.85T",
    volume = "52.3M",
    high24h = 251.20,
    low24h = 244.80,
    pe = "31.5",
    yearHigh = 255.00,
    yearLow = 164.08,
    avgVolume = "58.2M",
    description = "Apple Inc. designs, manufactures, and markets smartphones, personal computers, tablets, wearables, and accessories worldwide."
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockDetailsScreen(
    @Suppress("UNUSED_PARAMETER") isDarkTheme: Boolean = false,
    symbol: String = "AAPL",
    name: String = "Apple Inc.",
    onBackClick: () -> Unit = {}
) {
    var isFavorite by remember { mutableStateOf(false) }
    var selectedTimeframe by remember { mutableStateOf("1D") }
    val successColor = Color(0xFF10B981)
    val errorColor = Color(0xFFEF4444)

    // Get stock details based on symbol (using mock data for now)
    val stockDetails = remember(symbol) {
        getStockDetailsForSymbol(symbol, name)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                // Header with gradient and stock info
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.tertiary,
                                    MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f),
                                    MaterialTheme.colorScheme.background
                                ),
                                startY = 0f,
                                endY = 1400f
                            )
                        )
                        .padding(horizontal = 20.dp, vertical = 32.dp)
                ) {
                    Column {
                        // Top bar with back button and favorite
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(onClick = onBackClick) {
                                Icon(
                                    Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.White
                                )
                            }

                            IconButton(onClick = { isFavorite = !isFavorite }) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = "Favorite",
                                    tint = if (isFavorite) Color(0xFFFBBF24) else Color.White.copy(alpha = 0.4f)
                                )
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        // Stock symbol and name
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(Color.White.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    stockDetails.symbol.take(1),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }

                            Column {
                                Text(
                                    stockDetails.symbol,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    stockDetails.name,
                                    fontSize = 14.sp,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            }
                        }

                        Spacer(Modifier.height(24.dp))

                        // Current price
                        Text(
                            "$${"%,.2f".format(stockDetails.price)}",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(Modifier.height(8.dp))

                        // Price change
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                if (stockDetails.change >= 0) "▲" else "▼",
                                fontSize = 16.sp,
                                color = if (stockDetails.change >= 0) Color(0xFF34D399) else Color(0xFFFCA5A5)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                "$${"%,.2f".format(kotlin.math.abs(stockDetails.change))} (${if (stockDetails.change >= 0) "+" else ""}${stockDetails.changePercent}%) today",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (stockDetails.change >= 0) Color(0xFF34D399) else Color(0xFFFCA5A5)
                            )
                        }

                        Spacer(Modifier.height(32.dp))

                        // Chart timeframe selector
                        TimeframeSelector(
                            selected = selectedTimeframe,
                            onSelect = { selectedTimeframe = it }
                        )

                        Spacer(Modifier.height(16.dp))

                        // Detailed chart
                        StockChart(
                            data = detailedChartData,
                            isPositive = stockDetails.change >= 0
                        )

                        Spacer(Modifier.height(16.dp))
                    }
                }
            }

            item {
                // Buy/Sell Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { },
                        modifier = Modifier.weight(1f).height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = successColor
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            "Buy",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .border(2.dp, errorColor, RoundedCornerShape(16.dp)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = errorColor
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            "Sell",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {
                // Stats section
                Text(
                    "Key Statistics",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatsGrid(stock = mockStock)
                }
            }

            item {
                Spacer(Modifier.height(24.dp))

                // About section
                Text(
                    "About",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .shadow(2.dp, RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Text(
                        mockStock.description,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TimeframeSelector(
    selected: String,
    onSelect: (String) -> Unit
) {
    val timeframes = listOf("1D", "1W", "1M", "3M", "1Y", "ALL")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        timeframes.forEach { timeframe ->
            Surface(
                onClick = { onSelect(timeframe) },
                shape = RoundedCornerShape(12.dp),
                color = if (selected == timeframe)
                    Color.White.copy(alpha = 0.3f)
                else
                    Color.White.copy(alpha = 0.1f),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    timeframe,
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = if (selected == timeframe) FontWeight.Bold else FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun StockChart(data: List<Float>, isPositive: Boolean) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        val width = size.width
        val height = size.height
        val spacing = width / (data.size - 1)
        val max = data.maxOrNull() ?: 1f
        val min = data.minOrNull() ?: 0f
        val range = max - min

        val chartColor = if (isPositive) Color(0xFF34D399) else Color(0xFFFCA5A5)

        // Draw gradient fill under the line
        val fillPath = Path().apply {
            moveTo(0f, height)
            data.forEachIndexed { index, value ->
                val x = index * spacing
                val y = height - ((value - min) / range * height * 0.85f) - height * 0.05f
                if (index == 0) {
                    moveTo(x, y)
                } else {
                    lineTo(x, y)
                }
            }
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    chartColor.copy(alpha = 0.4f),
                    chartColor.copy(alpha = 0.05f)
                )
            )
        )

        // Draw the main line
        val linePath = Path()
        data.forEachIndexed { index, value ->
            val x = index * spacing
            val y = height - ((value - min) / range * height * 0.85f) - height * 0.05f
            if (index == 0) {
                linePath.moveTo(x, y)
            } else {
                linePath.lineTo(x, y)
            }
        }

        drawPath(
            path = linePath,
            color = chartColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )

        // Draw current price dot
        val lastValue = data.last()
        val lastX = (data.size - 1) * spacing
        val lastY = height - ((lastValue - min) / range * height * 0.85f) - height * 0.05f
        drawCircle(
            color = chartColor,
            radius = 6.dp.toPx(),
            center = Offset(lastX, lastY)
        )
        drawCircle(
            color = Color.White,
            radius = 3.dp.toPx(),
            center = Offset(lastX, lastY)
        )
    }
}

@Composable
fun StatsGrid(stock: StockDetails) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard("Market Cap", stock.marketCap, Modifier.weight(1f))
            StatCard("Volume", stock.volume, Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard("24h High", "$${"%,.2f".format(stock.high24h)}", Modifier.weight(1f))
            StatCard("24h Low", "$${"%,.2f".format(stock.low24h)}", Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard("52W High", "$${"%,.2f".format(stock.yearHigh)}", Modifier.weight(1f))
            StatCard("52W Low", "$${"%,.2f".format(stock.yearLow)}", Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard("P/E Ratio", stock.pe, Modifier.weight(1f))
            StatCard("Avg Volume", stock.avgVolume, Modifier.weight(1f))
        }
    }
}

@Composable
fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(4.dp))
            Text(
                value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// Helper function to get stock details based on symbol
fun getStockDetailsForSymbol(symbol: String, @Suppress("UNUSED_PARAMETER") name: String): StockDetails {
    return when (symbol) {
        "AAPL" -> StockDetails(
            symbol = "AAPL",
            name = "Apple Inc.",
            price = 250.15,
            change = 5.82,
            changePercent = 2.38,
            marketCap = "3.85T",
            volume = "52.3M",
            high24h = 251.20,
            low24h = 244.80,
            pe = "31.5",
            yearHigh = 255.00,
            yearLow = 164.08,
            avgVolume = "58.2M",
            description = "Apple Inc. designs, manufactures, and markets smartphones, personal computers, tablets, wearables, and accessories worldwide."
        )
        "TSLA" -> StockDetails(
            symbol = "TSLA",
            name = "Tesla",
            price = 245.80,
            change = -2.95,
            changePercent = -1.2,
            marketCap = "780B",
            volume = "98.5M",
            high24h = 248.90,
            low24h = 243.20,
            pe = "65.2",
            yearHigh = 299.29,
            yearLow = 138.80,
            avgVolume = "110.5M",
            description = "Tesla, Inc. designs, develops, manufactures, leases, and sells electric vehicles, and energy generation and storage systems worldwide."
        )
        "AMZN" -> StockDetails(
            symbol = "AMZN",
            name = "Amazon",
            price = 175.30,
            change = 1.40,
            changePercent = 0.8,
            marketCap = "1.82T",
            volume = "45.2M",
            high24h = 176.50,
            low24h = 173.80,
            pe = "52.8",
            yearHigh = 201.20,
            yearLow = 118.35,
            avgVolume = "50.3M",
            description = "Amazon.com, Inc. engages in the retail sale of consumer products and subscriptions in North America and internationally."
        )
        "BTC" -> StockDetails(
            symbol = "BTC",
            name = "Bitcoin",
            price = 67420.50,
            change = 3438.25,
            changePercent = 5.1,
            marketCap = "1.32T",
            volume = "28.5B",
            high24h = 68100.00,
            low24h = 64200.00,
            pe = "N/A",
            yearHigh = 73750.07,
            yearLow = 38520.00,
            avgVolume = "25.8B",
            description = "Bitcoin is a decentralized digital currency that can be transferred on the peer-to-peer bitcoin network without intermediaries."
        )
        else -> mockStock
    }
}
