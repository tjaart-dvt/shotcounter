package com.shotcounter.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shotcounter.ui.dashboard.Dashboard
import com.shotcounter.ui.recordshoot.RecordShoot
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.DASHBOARD.name) {
        composable(Screen.DASHBOARD.name) {
            Dashboard(navController = navController, viewModel = koinViewModel())
        }
        composable(Screen.RECORD_SHOOT.name) {
            RecordShoot(navController = navController, viewModel = koinViewModel())
        }
    }
}