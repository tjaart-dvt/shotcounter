package com.shotcounter.ui.domain.usecase.shotrecord

import android.media.AudioManager
import android.media.ToneGenerator

interface IPlayGoUseCase {
    fun execute()
}

class PlayGoUseCase: IPlayGoUseCase {
    override fun execute() {
        val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 140)
        toneGenerator.startTone(ToneGenerator.TONE_DTMF_P, 400)
    }
}