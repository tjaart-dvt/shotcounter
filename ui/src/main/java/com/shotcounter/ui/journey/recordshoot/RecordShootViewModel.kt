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
                _state.update {
                    RecordShootState.StartCountDown
                }
            }

            is RecordShootIntent.StartShoot -> {
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

            is RecordShootIntent.EndShoot -> {
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
    }
}