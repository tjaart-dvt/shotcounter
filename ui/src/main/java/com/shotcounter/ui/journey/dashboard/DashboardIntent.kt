package com.shotcounter.ui.journey.dashboard

sealed class DashboardIntent {
    data object RecordShoot: DashboardIntent()
    data object NavigationRequested: DashboardIntent()
}