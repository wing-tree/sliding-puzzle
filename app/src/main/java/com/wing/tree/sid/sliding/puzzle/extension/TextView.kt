package com.wing.tree.sid.sliding.puzzle.extension

import android.widget.TextView
import com.wing.tree.sid.core.extension.negative
import com.wing.tree.sid.core.extension.not
import kotlin.math.roundToInt

fun TextView.setDongleText(text: CharSequence) {
    this.text = text

    if (tag.not(true)) {
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

    tag = true
}
