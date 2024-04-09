package com.shotcounter.ui.domain.model.shotrecord

data class ShotModel(
    val isTimerRunning: Boolean = true,
    val time: Long = 0L,
    val shotTimesMs: List<Long> = listOf()
)
