package com.wing.tree.sid.sliding.puzzle.view

import com.wing.tree.sid.core.constant.ZERO
import com.wing.tree.sid.core.extension.long
import java.util.*
import kotlin.concurrent.timerTask


class Stopwatch(
    private val period: Long,
    private val callback: Callback,
    private val delay: Long = ZERO.long
) {
    private var state: State = State.Stop
    private var timer: Timer? = null

    fun reset() {
        if (state == State.Reset) {
            return
        } else {
            state = State.Reset

            timer?.cancelAndPurge()

            callback.onReset()
        }
    }

    fun start() {
        if (state == State.Start) {
            return
        } else {
            state = State.Start

            timer?.cancelAndPurge()

            timer = Timer()

            val timerTask = timerTask {
                callback.onTick(period)
            }

            timer?.scheduleAtFixedRate(timerTask, delay, period)

            callback.onStart()
        }
    }

    fun stop() {
        if (state == State.Stop) {
            return
        } else {
            state = State.Stop

            timer?.cancelAndPurge()

            callback.onStop()
        }
    }

    interface Callback {
        fun onReset() = Unit
        fun onStart() = Unit
        fun onStop() = Unit
        fun onTick(period: Long)
    }

    enum class State {
        Reset,
        Start,
        Stop
    }

    private fun Timer.cancelAndPurge() {
        cancel()
        purge()
    }
}
