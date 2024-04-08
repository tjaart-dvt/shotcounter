package com.shotcounter.ui.recordshoot

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun RecordShoot(navController: NavHostController, viewModel: RecordShootViewModel) {
    Text(text = "Record Shoot")
}