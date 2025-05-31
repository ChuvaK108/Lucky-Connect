package com.example.luckyconnect.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.luckyconnect.ui.screens.MainScreen
import com.example.luckyconnect.ui.screens.ServersScreen
import com.example.luckyconnect.ui.screens.SettingsScreen

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Servers : Screen("servers")
    object Settings : Screen("settings")
}

@Composable
fun VpnNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                onNavigateToServers = {
                    navController.navigate(Screen.Servers.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }
        
        composable(Screen.Servers.route) {
            ServersScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Settings.route) {
            SettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
} 