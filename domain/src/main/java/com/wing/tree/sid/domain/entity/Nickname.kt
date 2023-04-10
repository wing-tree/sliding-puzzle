package com.wing.tree.sid.domain.entity

import com.wing.tree.sid.core.constant.TEN

@JvmInline
value class Nickname(private val value: CharSequence) {
    override fun toString(): String {
        return "${value.take(MAX_LENGTH)}"
    }

    fun trim() = Nickname(value.trim())

    fun isNotBlank() = value.isNotBlank()

    companion object {
        const val MAX_LENGTH = TEN
    }
}