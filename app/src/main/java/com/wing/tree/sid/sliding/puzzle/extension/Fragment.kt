package com.wing.tree.sid.sliding.puzzle.extension

import androidx.annotation.DimenRes
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment

fun Fragment.getFloat(@DimenRes id: Int) = ResourcesCompat.getFloat(resources, id)
