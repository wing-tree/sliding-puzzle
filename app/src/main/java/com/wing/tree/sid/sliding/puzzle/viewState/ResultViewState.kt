package com.wing.tree.sid.sliding.puzzle.viewState

import com.wing.tree.sid.sliding.puzzle.model.Rank
import com.wing.tree.sid.sliding.puzzle.model.RankingParameter

sealed interface ResultViewState {
    object Loading : ResultViewState
    sealed class Content : ResultViewState {
        abstract val rankingParameter: RankingParameter

        data class Ranked(
            override val rankingParameter: RankingParameter,
            val rank: Rank
        ) : Content()

        data class NotRanked(
            override val rankingParameter: RankingParameter,
            val cause: Throwable
        ) : Content()
    }

    data class Error(val cause: Throwable) : ResultViewState
}
