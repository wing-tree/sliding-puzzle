package com.wing.tree.sid.sliding.puzzle.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wing.tree.sid.core.constant.TEN
import com.wing.tree.sid.core.constant.ZERO
import com.wing.tree.sid.core.useCase.Result.Complete
import com.wing.tree.sid.core.useCase.Result.Loading
import com.wing.tree.sid.domain.useCase.FetchRankingListUseCase
import com.wing.tree.sid.sliding.puzzle.firebase.FirebaseAuth
import com.wing.tree.sid.sliding.puzzle.mapper.RankingMapper
import com.wing.tree.sid.sliding.puzzle.model.Rank
import com.wing.tree.sid.sliding.puzzle.view.RankingListFragment.Companion.EXTRA_PAGE
import com.wing.tree.sid.sliding.puzzle.viewState.RankingListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingListViewModel @Inject constructor(
    private val fetchRankingListUseCase: FetchRankingListUseCase,
    private val firebaseAuth: FirebaseAuth,
    private val rankingMapper: RankingMapper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _viewState = MutableStateFlow<RankingListViewState>(RankingListViewState.Loading)
    val viewState: StateFlow<RankingListViewState> get() = _viewState

    private val page = savedStateHandle.get<Int>(EXTRA_PAGE) ?: ZERO

    init {
        viewModelScope.launch {
            fetch()
        }
    }

    private suspend fun fetch() {
        firebaseAuth
            .signInAnonymously()
            .onSuccess {
                val parameter = FetchRankingListUseCase.Parameter(page, PAGE_SIZE)
                val result = when (val result = fetchRankingListUseCase(parameter)) {
                    Loading -> Result.failure(IllegalStateException("result:$result"))
                    is Complete.Success -> result.data
                    is Complete.Failure -> Result.failure(result.cause)
                }

                result
                    .onSuccess { data ->
                        _viewState.update {
                            RankingListViewState.Content(
                                data.mapIndexed { index, ranking ->
                                    val value = index.inc().plus(page.times(PAGE_SIZE))

                                    rankingMapper.toModel(Rank(value), ranking)
                                }
                            )
                        }
                    }
                    .onFailure { exception ->
                        _viewState.update {
                            RankingListViewState.Error(exception)
                        }
                    }
            }
            .onFailure { exception ->
                _viewState.update {
                    RankingListViewState.Error(exception)
                }
            }
    }

    fun retry() {
        viewModelScope.launch {
            _viewState.update {
                RankingListViewState.Loading
            }

            fetch()
        }
    }

    companion object {
        private const val PAGE_SIZE = TEN
    }
}
