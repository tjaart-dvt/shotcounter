package com.shotcounter.ui.journey.recordshoot

import androidx.lifecycle.ViewModel
import com.shotcounter.ui.domain.usecase.shotrecord.IPlayGoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RecordShootViewModel(
    private val playGoSoundUseCase: IPlayGoUseCase
): ViewModel() {

    private val _state = MutableStateFlow<RecordShootState>(RecordShootState.Render)
    val state = _state.asStateFlow()

    fun onIntent(intent: RecordShootIntent) {
        when(intent) {
            is RecordShootIntent.StartShoot -> {
                playGoSoundUseCase.execute()
                _state.update {
                    RecordShootState.Record
                }
            }

            RecordShootIntent.StartCountDown -> {
                _state.update {
                    RecordShootState.StartCountDown
                }
            }
        }
    }
}