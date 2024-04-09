package com.shotcounter.ui.journey.recordshoot

sealed class RecordShootIntent {
    data object StartCountDown: RecordShootIntent()
    data object StartShoot: RecordShootIntent()
    data class EndShoot(val savedTime: Long = 0L): RecordShootIntent()
}