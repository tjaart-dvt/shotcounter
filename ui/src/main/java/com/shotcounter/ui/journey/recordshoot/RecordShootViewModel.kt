package com.shotcounter.ui.journey.recordshoot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shotcounter.ui.domain.usecase.shotrecord.IPlayGoUseCase
import com.shotcounter.ui.domain.usecase.shotrecord.ITimeShotsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecordShootViewModel(
    private val playGoSoundUseCase: IPlayGoUseCase,
    private val timeShotsUseCase: ITimeShotsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<RecordShootState>(RecordShootState.Render)
    val state = _state.asStateFlow()

    fun onIntent(intent: RecordShootIntent) {
        when (intent) {
            RecordShootIntent.StartCountDown -> {
                startCountDown()
            }

            is RecordShootIntent.StartShoot -> {
                recordShoot()
            }

            RecordShootIntent.EndShoot -> {
                displayResults()
            }
        }
    }

    private fun startCountDown() {
        _state.update {
            RecordShootState.StartCountDown
        }
    }

    private fun recordShoot() {
        playGoSoundUseCase.execute()
        viewModelScope.launch {
            timeShotsUseCase.start().collectLatest { timer ->
                _state.update {
                    RecordShootState.Record(
                        time = timer.time,
                        isTimerRunning = timer.isTimerRunning,
                        shotTimesMs = timer.shotTimesMs
                    )
                }
            }
        }
    }

    private fun displayResults() {
        timeShotsUseCase.stop()
        val results = timeShotsUseCase.results()
        _state.update {
            RecordShootState.Results(
                time = results.time,
                shotTimesMs = results.shotTimesMs,
                cadence = results.cadenceValues.average().toLong()
            )
        }
    }
}