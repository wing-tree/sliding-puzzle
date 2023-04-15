package com.wing.tree.sid.sliding.puzzle.extension

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.annotation.DimenRes
import androidx.core.view.isVisible
import com.wing.tree.sid.core.constant.ONE
import com.wing.tree.sid.core.constant.ZERO
import com.wing.tree.sid.core.extension.float
import com.wing.tree.sid.core.extension.long

val View.isNotVisible: Boolean get() = isVisible.not()

fun View.dimensionPixelSize(@DimenRes id: Int) = resources.getDimensionPixelSize(id)

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.fadeIn(
    duration: Long = context.configLongAnimTime.long,
    startDelay: Long = ZERO.long,
    interpolator: TimeInterpolator? = context.decelerateQuadInterpolator,
    listener: Animator.AnimatorListener? = null
): ViewPropertyAnimator {
    visible()

    alpha = ZERO.float

    return animate()
        .alpha(ONE.float)
        .setDuration(duration)
        .setStartDelay(startDelay)
        .setInterpolator(interpolator)
        .setListener(listener)
        .withLayer()
}

fun View.fadeOut(
    duration: Long = context.configMediumAnimTime.long,
    startDelay: Long = ZERO.long,
    interpolator: TimeInterpolator? = context.accelerateQuadInterpolator,
    listener: Animator.AnimatorListener? = null
): ViewPropertyAnimator {
    alpha = ONE.float

    return animate()
        .alpha(ZERO.float)
        .setDuration(duration)
        .setStartDelay(startDelay)
        .setInterpolator(interpolator)
        .setListener(listener)
        .withLayer()
}

fun View.slideDown(duration: Long): ObjectAnimator {
    val objectAnimator = ObjectAnimator
        .ofFloat(this, "y", y, y.plus(height))
        .setDuration(duration)

    return objectAnimator.also {
        it.start()
    }
}

fun View.slideLeft(duration: Long): ObjectAnimator {
    val objectAnimator = ObjectAnimator
        .ofFloat(this, "x", x, x.minus(width))
        .setDuration(duration)

    return objectAnimator.also {
        it.start()
    }
}

fun View.slideRight(duration: Long): ObjectAnimator {
    val objectAnimator = ObjectAnimator
        .ofFloat(this, "x", x, x.plus(width))
        .setDuration(duration)

    return objectAnimator.also {
        it.start()
    }
}

fun View.slideUp(duration: Long): ObjectAnimator {
    val objectAnimator = ObjectAnimator
        .ofFloat(this, "y", y, y.minus(height))
        .setDuration(duration)

    return objectAnimator.also {
        it.start()
    }
}
