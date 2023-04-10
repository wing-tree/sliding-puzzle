package com.wing.tree.sid.sliding.puzzle.extension

import android.annotation.SuppressLint
import android.content.res.Resources
import com.wing.tree.sid.core.constant.ZERO
import com.wing.tree.sid.core.extension.isPositive

val Resources.configShortAnimTime: Int get() = getInteger(android.R.integer.config_shortAnimTime)
val Resources.configMediumAnimTime: Int get() = getInteger(android.R.integer.config_mediumAnimTime)
val Resources.configLongAnimTime: Int get() = getInteger(android.R.integer.config_longAnimTime)
val Resources.configShowNavigationBar: Boolean get() = run {
    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    val identifier = getIdentifier(
        "config_showNavigationBar",
        "bool",
        "android"
    )

    if (identifier.isPositive) {
        getBoolean(identifier)
    } else {
        false
    }
}

val Resources.navigationBarHeight: Int get() = run {
    if (configShowNavigationBar.not()) {
        return ZERO
    }

    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    val identifier = getIdentifier(
        "navigation_bar_height",
        "dimen",
        "android"
    )

    if (identifier.isPositive) {
        getDimensionPixelSize(identifier)
    } else {
        ZERO
    }
}

val Resources.statusBarHeight: Int get() = run {
    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    val identifier = getIdentifier(
        "status_bar_height",
        "dimen",
        "android"
    )

    if (identifier.isPositive) {
        getDimensionPixelSize(identifier)
    } else {
        ZERO
    }
}
