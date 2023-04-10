package com.wing.tree.sid.sliding.puzzle.viewState

import com.wing.tree.sid.sliding.puzzle.model.Puzzle

sealed interface PlayViewState {
    object Loading : PlayViewState
    sealed class Content : PlayViewState {
        data class Playing(val puzzle: Puzzle) : Content()
        object Solved : Content()
    }

    data class Error(val cause: Throwable) : PlayViewState
}
