package com.shotcounter.ui.journey.recordshoot

sealed class RecordShootState {
    data object Render: RecordShootState()
    data object StartCountDown: RecordShootState()
    data class Record(
        var time: Long = 0L,
        var isTimerRunning: Boolean = true,
        val shotTimesMs: List<Long> = listOf()
    ): RecordShootState()
    data class Results(
        var time: Long = 0L,
        val shotTimesMs: List<Long> = listOf(),
        val cadence: Long = 0
    ): RecordShootState()
}