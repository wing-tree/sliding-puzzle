package com.wing.tree.sid.core.extension

fun <T> MutableList<T>.clearAndAddAll(c: Collection<T>) {
    clear()
    addAll(c)
}
