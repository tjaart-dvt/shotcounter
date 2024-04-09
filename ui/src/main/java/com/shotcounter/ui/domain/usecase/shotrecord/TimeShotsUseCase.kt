package com.shotcounter.ui.domain.usecase.shotrecord

import com.shotcounter.ui.domain.model.shotrecord.ShotModel
import kotlinx.coroutines.delay
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

    private val timeGap = 50L
    private val maxShots = 10
    private val randomIndex = 100000
    override fun start() = flow<ShotModel> {
        while (isRunning) {
            delay(timeGap)
            time += timeGap
            emit(ShotModel(
                isTimerRunning = isRunning,
                time = time,
                shotTimesMs = shots.toList()
            ))

            val randomNumExclusive = (0 until randomIndex).random()

            if(shots.size < maxShots && randomNumExclusive > (randomIndex - 1)) {
                shots.add(time)
            }
            if(shots.size >= maxShots) {
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
            if(shots.indices.contains(index + 1)) {
                cadenceValues.add(shots[index + 1] - value)
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