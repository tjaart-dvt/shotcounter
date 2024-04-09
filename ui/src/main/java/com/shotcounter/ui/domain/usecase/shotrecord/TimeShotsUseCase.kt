package com.shotcounter.ui.domain.usecase.shotrecord

import android.util.Log
import com.shotcounter.ui.domain.model.shotrecord.ShotModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow

interface ITimeShotsUseCase {
    fun start(): kotlinx.coroutines.flow.Flow<ShotModel>
    fun stop()

    fun results(): ShotModel
}

class TimeShotsUseCase : ITimeShotsUseCase {
    private var isRunning = true
    private var time = 0L
    private var shots = mutableListOf<Long>()
    override fun start() = flow<ShotModel> {
        while (isRunning) {
            delay(50)
            time += 50
            emit(ShotModel(
                isTimerRunning = isRunning,
                time = time,
                shotTimesMs = shots.toList()
            ))

            val randomNumExclusive = (0 until 100000).random()

            if(shots.size < 10 && randomNumExclusive > 99000) {
                shots.add(time)
            }
            if(shots.size >= 10) {
                emit(ShotModel(
                    isTimerRunning = isRunning,
                    time = time,
                    shotTimesMs = shots.toList()
                ))
                isRunning = false
            }
        }
    }

    override fun stop() {
        isRunning = false
    }

    override fun results(): ShotModel {
        val cadenceValues = mutableListOf<Long>()
        shots.forEachIndexed { index, value ->

            if(shots.indices.contains(index +1)) {
                cadenceValues.add(shots[index+1] - value)
            }
        }
        return ShotModel(
            isTimerRunning = false,
            time = time,
            shotTimesMs = shots,
            cadenceValues = cadenceValues
        )
    }
}