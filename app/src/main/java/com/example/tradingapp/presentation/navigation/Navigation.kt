package com.example.tradingapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tradingapp.presentation.marketdetails.StockDetailsScreen
import com.example.tradingapp.presentation.portfolio.PortfolioScreen
import com.example.tradingapp.presentation.market.MarketScreen

sealed class Screen(val route: String) {
    object Portfolio : Screen("portfolio")
    object Market : Screen("market")
    object StockDetails : Screen("stock_details/{symbol}/{name}") {
        fun createRoute(symbol: String, name: String) = "stock_details/$symbol/$name"
    }
}

@Composable
fun TradingAppNavigation(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Portfolio.route
    ) {
        composable(Screen.Portfolio.route) {
            PortfolioScreen(
                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle,
                onAssetClick = { symbol, name ->
                    navController.navigate(Screen.StockDetails.createRoute(symbol, name))
                },
                onMarketClick = {
                    navController.navigate(Screen.Market.route)
                }
            )
        }

        composable(Screen.Market.route) {
            MarketScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.StockDetails.route,
            arguments = listOf(
                navArgument("symbol") { type = NavType.StringType },
                navArgument("name") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val symbol = backStackEntry.arguments?.getString("symbol") ?: "ERROR"
            val name = backStackEntry.arguments?.getString("name") ?: "Connection Error"

            StockDetailsScreen(
                isDarkTheme = isDarkTheme,
                symbol = symbol,
                name = name,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
