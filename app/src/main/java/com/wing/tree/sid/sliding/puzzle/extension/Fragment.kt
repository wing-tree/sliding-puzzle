@file:Suppress("unused")

package com.wing.tree.sid.sliding.puzzle.extension

import androidx.annotation.DimenRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment

val Fragment.configShortAnimTime: Int get() = resources.getInteger(android.R.integer.config_shortAnimTime)
val Fragment.configMediumAnimTime: Int get() = resources.getInteger(android.R.integer.config_mediumAnimTime)
val Fragment.configLongAnimTime: Int get() = resources.getInteger(android.R.integer.config_longAnimTime)

fun Fragment.float(@DimenRes id: Int) = ResourcesCompat.getFloat(resources, id)
