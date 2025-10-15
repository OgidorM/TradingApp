package com.example.tradingapp.presentation.portfolio

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import kotlin.math.sin

// Mock data classes
data class Asset(
    val name: String,
    val symbol: String,
    val value: Double,
    val change: Double,
    val shares: Double,
    val iconColor: Color
)

val mockAssets = listOf(
    Asset("Apple Inc.", "AAPL", 15000.0, 2.5, 87.5, Color(0xFF555555)),
    Asset("Tesla", "TSLA", 8000.0, -1.2, 32.0, Color(0xFFE82127)),
    Asset("Amazon", "AMZN", 12000.0, 0.8, 9.0, Color(0xFFFF9900)),
    Asset("Bitcoin", "BTC", 25000.0, 5.1, 0.625, Color(0xFFF7931A))
)

// Generate fake chart data
val chartData = listOf(
    54000f, 54500f, 54200f, 55000f, 55800f, 55400f, 56200f,
    57000f, 56500f, 57800f, 58200f, 57900f, 58800f, 59200f,
    58900f, 59500f, 60000f, 59800f, 60200f, 60000f
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioScreen(
    isDarkTheme: Boolean = false,
    onThemeToggle: () -> Unit = {},
    onAssetClick: (symbol: String, name: String) -> Unit = { _, _ -> },
    onMarketClick: () -> Unit = {}
) {
    val successColor = Color(0xFF10B981)
    val errorColor = Color(0xFFEF4444)

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
                // Header with gradient, balance, and chart blended together
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
                                endY = 1200f
                            )
                        )
                        .padding(horizontal = 20.dp, vertical = 32.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Portfolio",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Market button
                                Surface(
                                    shape = RoundedCornerShape(24.dp),
                                    color = Color.White.copy(alpha = 0.2f),
                                    onClick = onMarketClick,
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(
                                            "📊",
                                            fontSize = 16.sp
                                        )
                                        Text(
                                            "Market",
                                            color = Color.White,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }

                                // Modern Theme Toggle with emoji
                                Surface(
                                    shape = RoundedCornerShape(24.dp),
                                    color = Color.White.copy(alpha = 0.2f),
                                    onClick = onThemeToggle,
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            if (isDarkTheme) "🌙" else "☀️",
                                            fontSize = 18.sp
                                        )
                                        Text(
                                            if (isDarkTheme) "Dark" else "Light",
                                            color = Color.White,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.height(32.dp))

                        // Balance without card - blended in gradient
                        PortfolioBalanceSection(
                            balance = 60000.0,
                            dailyChange = 1250.50,
                            successColor = successColor,
                            errorColor = errorColor
                        )

                        Spacer(Modifier.height(32.dp))

                        // Chart without card - blended in gradient
                        PortfolioChart(data = chartData)

                        Spacer(Modifier.height(16.dp))
                    }
                }
            }

            item {
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Your Assets",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        "${mockAssets.size} holdings",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
                Spacer(Modifier.height(12.dp))
            }

            items(mockAssets) { asset ->
                ModernAssetCard(
                    asset = asset,
                    successColor = successColor,
                    errorColor = errorColor,
                    onClick = { onAssetClick(asset.symbol, asset.name) }
                )
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun PortfolioBalanceSection(
    balance: Double,
    dailyChange: Double,
    @Suppress("UNUSED_PARAMETER") successColor: Color,
    @Suppress("UNUSED_PARAMETER") errorColor: Color
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            "Total Balance",
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.8f),
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "$${"%,.2f".format(balance)}",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                if (dailyChange >= 0) "▲" else "▼",
                fontSize = 18.sp,
                color = if (dailyChange >= 0) Color(0xFF34D399) else Color(0xFFFCA5A5)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                "$${"%,.2f".format(kotlin.math.abs(dailyChange))} today",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (dailyChange >= 0) Color(0xFF34D399) else Color(0xFFFCA5A5)
            )
        }
    }
}

@Composable
fun PortfolioChart(data: List<Float>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Performance (7 Days)",
            fontSize = 14.sp,
            color = Color.White.copy(alpha = 0.7f),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        ) {
            val width = size.width
            val height = size.height
            val spacing = width / (data.size - 1)
            val max = data.maxOrNull() ?: 1f
            val min = data.minOrNull() ?: 0f
            val range = max - min

            // Draw gradient fill under the line
            val fillPath = Path().apply {
                moveTo(0f, height)
                data.forEachIndexed { index, value ->
                    val x = index * spacing
                    val y = height - ((value - min) / range * height * 0.8f) - height * 0.1f
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
                        Color(0xFF34D399).copy(alpha = 0.3f),
                        Color(0xFF34D399).copy(alpha = 0.05f)
                    )
                )
            )

            // Draw the main line
            val linePath = Path()
            data.forEachIndexed { index, value ->
                val x = index * spacing
                val y = height - ((value - min) / range * height * 0.8f) - height * 0.1f
                if (index == 0) {
                    linePath.moveTo(x, y)
                } else {
                    linePath.lineTo(x, y)
                }
            }

            drawPath(
                path = linePath,
                color = Color(0xFF34D399),
                style = Stroke(
                    width = 3.dp.toPx(),
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                )
            )

            // Draw dots at data points
            data.forEachIndexed { index, value ->
                val x = index * spacing
                val y = height - ((value - min) / range * height * 0.8f) - height * 0.1f
                if (index % 4 == 0) { // Only show some dots to avoid clutter
                    drawCircle(
                        color = Color(0xFF34D399),
                        radius = 4.dp.toPx(),
                        center = Offset(x, y)
                    )
                    drawCircle(
                        color = Color.White.copy(alpha = 0.3f),
                        radius = 2.dp.toPx(),
                        center = Offset(x, y)
                    )
                }
            }
        }
    }
}

@Composable
fun ModernAssetCard(
    asset: Asset,
    successColor: Color,
    errorColor: Color,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Asset Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(asset.iconColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    asset.symbol.take(1),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = asset.iconColor
                )
            }

            Spacer(Modifier.width(16.dp))

            // Asset Info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    asset.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "${asset.shares} shares",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    fontSize = 13.sp
                )
            }

            // Value & Change
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "$${"%,.2f".format(asset.value)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(4.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (asset.change >= 0)
                        successColor.copy(alpha = 0.1f)
                    else
                        errorColor.copy(alpha = 0.1f)
                ) {
                    Text(
                        "${if (asset.change >= 0) "+" else ""}${asset.change}%",
                        color = if (asset.change >= 0) successColor else errorColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}
