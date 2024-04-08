package com.shotcounter.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.shotcounter.ui.navigation.observe

@Composable
fun Dashboard(navController: NavHostController, viewModel: DashboardViewModel) {
    val state by viewModel.state.collectAsState()

    DashboardContent(onClick = viewModel::onIntent)

    navController.observe(state.navRoute) {
        viewModel.onIntent(DashboardIntent.NavigationRequested)
    }
}

@Composable
private fun DashboardContent(
    onClick: (DashboardIntent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            DashboardHeading()
            Spacer(modifier = Modifier.weight(0.3f))
            RecordNewShootButton(onClick)
            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
}