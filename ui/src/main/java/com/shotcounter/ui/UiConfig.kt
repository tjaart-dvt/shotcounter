package com.shotcounter.ui

import com.shotcounter.ui.dashboard.DashboardViewModel
import com.shotcounter.ui.recordshoot.RecordShootViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object UiConfig {
    val module = module {
        viewModel { DashboardViewModel() }
        viewModel { RecordShootViewModel() }
    }
}