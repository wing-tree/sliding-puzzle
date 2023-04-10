package com.wing.tree.sid.core.extension

val List<Int>.isOrdered: Boolean get() = zipWithNext().all {
    it.first < it.second
}

inline fun <reified T> List<*>.indexOfFirst(): Int {
    return indexOfFirst { it is T }
}
