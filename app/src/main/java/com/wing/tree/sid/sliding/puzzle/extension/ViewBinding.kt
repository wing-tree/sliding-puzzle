package com.wing.tree.sid.sliding.puzzle.extension

import android.animation.Animator
import android.animation.TimeInterpolator
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import com.wing.tree.sid.core.constant.ONE
import com.wing.tree.sid.core.constant.ZERO
import com.wing.tree.sid.core.extension.float
import com.wing.tree.sid.core.extension.long

val ViewBinding.context: Context get() = root.context
val ViewBinding.resources: Resources get() = context.resources

fun ViewBinding.visible() {
    root.visibility = View.VISIBLE
}

fun ViewBinding.gone() {
    root.visibility = View.GONE
}

fun ViewBinding.setOnClickListener(l: View.OnClickListener?) {
    root.setOnClickListener(l)
}

fun ViewBinding.setOnTouchListener(l: View.OnTouchListener) {
    root.setOnTouchListener(l)
}

fun ViewBinding.fadeIn(
    duration: Long = context.configLongAnimTime.long,
    interpolator: TimeInterpolator? = context.decelerateQuadInterpolator,
    listener: Animator.AnimatorListener? = null
): ViewPropertyAnimator {
    return root.fadeIn(
        duration,
        interpolator,
        listener
    )
}

fun ViewBinding.fadeOut(
    duration: Long = context.configMediumAnimTime.long,
    interpolator: TimeInterpolator? = context.accelerateQuadInterpolator,
    listener: Animator.AnimatorListener? = null
): ViewPropertyAnimator {
    return root.fadeOut(
        duration,
        interpolator,
        listener
    )
}

fun ViewBinding.integer(@IntegerRes id: Int) = resources.getInteger(id)
fun ViewBinding.string(@StringRes id: Int) = resources.getString(id)
