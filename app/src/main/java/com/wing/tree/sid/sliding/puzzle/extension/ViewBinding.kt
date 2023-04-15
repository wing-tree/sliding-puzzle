@file:Suppress("unused")

package com.wing.tree.sid.sliding.puzzle.extension

import android.animation.Animator
import android.animation.TimeInterpolator
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import androidx.annotation.DimenRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import androidx.viewbinding.ViewBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.wing.tree.sid.core.constant.ONE_HUNDRED
import com.wing.tree.sid.core.constant.ZERO
import com.wing.tree.sid.core.extension.long
import com.wing.tree.sid.sliding.puzzle.BuildConfig
import com.wing.tree.sid.sliding.puzzle.R

val ViewBinding.context: Context get() = root.context
val ViewBinding.resources: Resources get() = context.resources

val ViewBinding.configShortAnimTime: Int get() = resources.configShortAnimTime
val ViewBinding.configMediumAnimTime: Int get() = resources.configMediumAnimTime
val ViewBinding.configLongAnimTime: Int get() = resources.configLongAnimTime

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
    startDelay: Long = ZERO.long,
    interpolator: TimeInterpolator? = context.decelerateQuadInterpolator,
    listener: Animator.AnimatorListener? = null
): ViewPropertyAnimator {
    return root.fadeIn(
        duration = duration,
        startDelay = startDelay,
        interpolator = interpolator,
        listener = listener,
    )
}

fun ViewBinding.fadeOut(
    duration: Long = context.configMediumAnimTime.long,
    startDelay: Long = ZERO.long,
    interpolator: TimeInterpolator? = context.accelerateQuadInterpolator,
    listener: Animator.AnimatorListener? = null
): ViewPropertyAnimator {
    return root.fadeOut(
        duration = duration,
        startDelay = startDelay,
        interpolator = interpolator,
        listener = listener,
    )
}

fun ViewBinding.float(@DimenRes id: Int): Float = ResourcesCompat.getFloat(resources, id)
fun ViewBinding.integer(@IntegerRes id: Int) = resources.getInteger(id)
fun ViewBinding.string(@StringRes id: Int) = resources.getString(id)

fun ViewBinding.bannerAd(parent: ViewGroup, adSize: AdSize) {
    val adRequest = AdRequest.Builder().build()

    @StringRes
    val resId = if (BuildConfig.DEBUG) {
        R.string.sample_banner_ad_unit_id
    } else {
        R.string.banner_ad_unit_id
    }

    parent.gone()

    AdView(context).apply {
        val adView = this

        adUnitId = string(resId)
        adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()

                with(parent) {
                    removeView(adView)
                    addView(adView)

                    fadeIn(startDelay = ONE_HUNDRED.long)
                }
            }
        }

        setAdSize(adSize)
    }.loadAd(adRequest)
}
