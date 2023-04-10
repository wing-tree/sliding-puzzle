package com.wing.tree.sid.sliding.puzzle.viewState

import com.wing.tree.sid.sliding.puzzle.model.Ranking

sealed interface RankingListViewState {
    object Loading : RankingListViewState
    data class Content(val data: List<Ranking>) : RankingListViewState
    data class Error(val cause: Throwable) : RankingListViewState
}
