package com.wing.tree.sid.sliding.puzzle.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import com.wing.tree.sid.core.extension.double
import kotlin.math.atan2

private typealias Angle = Double

abstract class OnFlingListener(context: Context) : View.OnTouchListener {
    abstract fun onFling(direction: Direction): Boolean

    private val gestureDetector: GestureDetector by lazy {
        GestureDetector(context, GestureListener())
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    private fun MotionEvent.angle(other: MotionEvent): Angle {
        val y = y.minus(other.y).double
        val x = other.x.minus(x).double

        return atan2(y, x)
            .plus(Math.PI)
            .times(180.0)
            .div(Math.PI)
            .plus(180.0)
            .rem(360.0)
    }

    private fun MotionEvent.direction(other: MotionEvent): Direction {
        return angle(other).direction
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            return onFling(e1.direction(e2))
        }
    }

    enum class Direction {
        Up, Down, Left, Right
    }

    private val Angle.direction: Direction
        get() = when (this) {
        in 45.0.rangeTo(135.0) -> Direction.Up
        in 0.0.rangeTo(45.0), in 315.0.rangeTo(360.0) -> Direction.Right
        in 225.0.rangeTo(315.0) -> Direction.Down
        else -> Direction.Left
    }


    val Direction.dragFlag: Int get() = when(this) {
        Direction.Up -> ItemTouchHelper.UP
        Direction.Down -> ItemTouchHelper.DOWN
        Direction.Left -> ItemTouchHelper.LEFT
        Direction.Right -> ItemTouchHelper.RIGHT
    }
}
