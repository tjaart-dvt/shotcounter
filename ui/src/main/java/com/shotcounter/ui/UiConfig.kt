package com.shotcounter.ui

import com.shotcounter.ui.journey.dashboard.DashboardViewModel
import com.shotcounter.ui.journey.recordshoot.RecordShootViewModel
import com.shotcounter.ui.domain.usecase.shotrecord.IPlayGoUseCase
import com.shotcounter.ui.domain.usecase.shotrecord.PlayGoUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object UiConfig {
    val module = module {
        factory<IPlayGoUseCase> { PlayGoUseCase() }

        viewModel { DashboardViewModel() }
        viewModel { RecordShootViewModel( get() ) }
    }
}