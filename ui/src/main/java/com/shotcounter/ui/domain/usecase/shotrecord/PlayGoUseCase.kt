package com.shotcounter.ui.domain.usecase.shotrecord

import android.media.AudioManager
import android.media.ToneGenerator

interface IPlayGoUseCase {
    fun execute()
}

class PlayGoUseCase: IPlayGoUseCase {
    override fun execute() {
        val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 400)
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_HIGH_L, 400)
    }
}