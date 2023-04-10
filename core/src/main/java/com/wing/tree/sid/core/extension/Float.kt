package com.wing.tree.sid.core.extension

val Float.double: Double get() = toDouble()
val Float.negative: Float get() = unaryMinus()
