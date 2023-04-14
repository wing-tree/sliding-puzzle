package com.wing.tree.sid.sliding.puzzle.extension

import android.widget.TextView
import androidx.annotation.StringRes
import com.wing.tree.sid.core.extension.negative
import kotlin.math.roundToInt

fun TextView.setDongleText(text: CharSequence) {
    this.text = text

    val fontMetrics = paint.fontMetrics
    val ascent = fontMetrics.ascent
    val top = fontMetrics.top

    setPadding(
        paddingLeft,
        paddingTop.plus(ascent.minus(top).roundToInt().negative),
        paddingRight,
        paddingBottom
    )
}

fun TextView.setDongleText(@StringRes resid: Int) {
    setDongleText(resources.getString(resid))
}
