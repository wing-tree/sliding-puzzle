package com.wing.tree.sid.domain.entity

import com.wing.tree.sid.core.constant.*

enum class Size(
    val row: Int,
    val column: Int
) {
    Eight(THREE, THREE),
    Fifteen(FOUR, FOUR),
    TwentyFour(FIVE, FIVE),
    ThirtyFive(SIX, SIX);

    val int: Int get() = when(this) {
        Eight -> EIGHT
        Fifteen -> FIFTEEN
        TwentyFour -> TWENTY_FOUR
        ThirtyFive -> THIRTY_FIVE
    }

    companion object {
        const val KEY = "size"

        val default = Fifteen
    }
}
