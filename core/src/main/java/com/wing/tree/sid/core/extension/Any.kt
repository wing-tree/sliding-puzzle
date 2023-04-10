package com.wing.tree.sid.core.extension

infix fun Any?.`is`(other: Any?) = this == other
infix fun Any?.not(other: Any?) = this.`is`(other).not()

inline fun <T> T?.ifNull(defaultValue: () -> T): T = this ?: defaultValue()
