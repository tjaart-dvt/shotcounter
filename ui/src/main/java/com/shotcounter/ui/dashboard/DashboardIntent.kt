package com.shotcounter.ui.dashboard

sealed class DashboardIntent {
    data object RecordShoot: DashboardIntent()
    data object NavigationRequested: DashboardIntent()
}