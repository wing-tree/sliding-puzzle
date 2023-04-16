package com.wing.tree.sid.core.extension

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

infix fun Any?.`is`(other: Any?) = this == other
infix fun Any?.not(other: Any?) = this.`is`(other).not()

@OptIn(ExperimentalContracts::class)
fun Any?.isNull(): Boolean {
    contract {
        returns(false) implies (this@isNull != null)
    }

    return this == null
}

@OptIn(ExperimentalContracts::class)
fun Any?.isNotNull(): Boolean {
    contract {
        returns(true) implies (this@isNotNull != null)
    }

    return this != null
}

inline fun <T> T?.ifNull(defaultValue: () -> T): T = this ?: defaultValue()
