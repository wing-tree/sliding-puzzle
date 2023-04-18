package com.wing.tree.sid.sliding.puzzle.extension

import com.wing.tree.sid.sliding.puzzle.databinding.ErrorBinding

fun ErrorBinding.hide() {
    fadeOut().withEndAction {
        gone()
    }
}

fun ErrorBinding.show(cause: Throwable? = null) {
    fadeIn().withStartAction {
        cause?.message?.let {
            message.text = it
        }

        visible()
    }
}
