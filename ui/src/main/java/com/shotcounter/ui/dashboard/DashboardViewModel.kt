package com.shotcounter.ui.dashboard

import androidx.lifecycle.ViewModel
import com.shotcounter.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DashboardViewModel : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

    fun onIntent(intent: DashboardIntent) {
        when (intent) {
            is DashboardIntent.RecordShoot -> {
                navigateToRecordNewShoot()
            }

            is DashboardIntent.NavigationRequested -> {
                onNavigated()
            }
        }
    }

    private fun navigateToRecordNewShoot() {
        _state.update {
            it.copy(
                navRoute = Screen.RECORD_SHOOT
            )
        }
    }

    private fun onNavigated() {
        _state.update {
            it.copy(
                navRoute = null
            )
        }
    }
}