package com.wing.tree.sid.sliding.puzzle.model

import com.wing.tree.sid.core.extension.ordinal

@JvmInline
value class Rank(private val value: Int) {
    override fun toString(): String {
        return value.ordinal
    }
}
