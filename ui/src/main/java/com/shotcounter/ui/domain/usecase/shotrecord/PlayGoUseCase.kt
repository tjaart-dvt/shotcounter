package com.shotcounter.ui.domain.usecase.shotrecord

import android.media.AudioManager
import android.media.ToneGenerator

interface IPlayGoUseCase {
    fun execute()
}

class PlayGoUseCase: IPlayGoUseCase {
    private val volume = 140
    private val durationMs = 400

    override fun execute() {
        val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, volume)
        toneGenerator.startTone(ToneGenerator.TONE_DTMF_P, durationMs)
    }
}