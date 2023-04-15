package com.wing.tree.sid.sliding.puzzle.extension

import android.content.Context
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import androidx.annotation.DimenRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat

val Context.configShortAnimTime: Int get() = resources.configShortAnimTime
val Context.configMediumAnimTime: Int get() = resources.configMediumAnimTime
val Context.configLongAnimTime: Int get() = resources.configLongAnimTime

val Context.accelerateCubicInterpolator: Interpolator
    get() = AnimationUtils.loadInterpolator(this, android.R.interpolator.accelerate_cubic)

val Context.accelerateQuadInterpolator: Interpolator
    get() = AnimationUtils.loadInterpolator(this, android.R.interpolator.accelerate_quad)

val Context.accelerateQuintInterpolator: Interpolator
    get() = AnimationUtils.loadInterpolator(this, android.R.interpolator.accelerate_quint)

val Context.decelerateCubicInterpolator: Interpolator
    get() = AnimationUtils.loadInterpolator(this, android.R.interpolator.decelerate_cubic)

val Context.decelerateQuadInterpolator: Interpolator
    get() = AnimationUtils.loadInterpolator(this, android.R.interpolator.decelerate_quad)

val Context.decelerateQuintInterpolator: Interpolator
    get() = AnimationUtils.loadInterpolator(this, android.R.interpolator.decelerate_quint)

val Context.statusBarHeight: Int get() = resources.statusBarHeight
val Context.navigationBarHeight: Int get() = resources.navigationBarHeight

fun Context.dimensionPixelSize(@DimenRes id: Int) = resources.getDimensionPixelSize(id)
fun Context.float(@DimenRes id: Int) = ResourcesCompat.getFloat(resources, id)
fun Context.integer(@IntegerRes id: Int) = resources.getInteger(id)
fun Context.string(@StringRes id: Int) = resources.getString(id)
