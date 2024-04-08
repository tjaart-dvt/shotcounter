package com.shotcounter.ui.journey.recordshoot

sealed class RecordShootState {
    data object Loading: RecordShootState()
    data object Render: RecordShootState()
    data object StartCountDown: RecordShootState()
    data object Record: RecordShootState()
    data class Results(val data: ArrayList<String>): RecordShootState()
}