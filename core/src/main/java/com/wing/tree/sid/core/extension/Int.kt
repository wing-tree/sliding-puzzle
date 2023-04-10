package com.wing.tree.sid.core.extension

import com.wing.tree.sid.core.suffix.Suffix
import com.wing.tree.sid.core.constant.*

val Int.float: Float get() = toFloat()
val Int.hundreds: Int get() = times(ONE_HUNDRED)
val Int.isEven: Boolean get() = rem(TWO) == ZERO
val Int.isOdd: Boolean get() = isEven.not()
val Int.isPositive: Boolean get() = this > ZERO
val Int.isZero: Boolean get() = this == ZERO
val Int.long: Long get() = toLong()
val Int.milliseconds: Long get() = toLong()
val Int.negative: Int get() = unaryMinus()
val Int.ordinal: String get() = run {
    val suffix = when (rem(ONE_HUNDRED)) {
        ELEVEN, TWELVE, THIRTEEN -> Suffix.TH
        else -> when (rem(TEN)) {
            ONE -> Suffix.ST
            TWO -> Suffix.ND
            THREE -> Suffix.RD
            else -> Suffix.TH
        }
    }

    return "$this$suffix"
}
