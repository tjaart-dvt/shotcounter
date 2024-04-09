package com.shotcounter.ui.journey.recordshoot

sealed class RecordShootState {
    data object Render : RecordShootState()
    data object StartCountDown : RecordShootState()
    data class Record(
        var time: Long = 0L,
        var isTimerRunning: Boolean = true,
        val shotTimesMs: List<Long>
    ) : RecordShootState()

    data class Results(var time: Long, val shotTimesMs: List<Long>, val cadence: Long) :
        RecordShootState()
}