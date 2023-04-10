package com.wing.tree.sid.sliding.puzzle.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wing.tree.sid.core.constant.EMPTY
import com.wing.tree.sid.core.useCase.Result.Complete
import com.wing.tree.sid.core.useCase.Result.Loading
import com.wing.tree.sid.core.useCase.onFailure
import com.wing.tree.sid.core.useCase.onSuccess
import com.wing.tree.sid.core.useCase.propagate
import com.wing.tree.sid.data.mapper.SizeMapper
import com.wing.tree.sid.domain.entity.Nickname
import com.wing.tree.sid.domain.entity.Ranking
import com.wing.tree.sid.domain.useCase.CheckRankingCriteriaUseCase
import com.wing.tree.sid.domain.useCase.RegisterForRankingUseCase
import com.wing.tree.sid.domain.useCase.ResetPuzzleUseCase
import com.wing.tree.sid.sliding.puzzle.firebase.FirebaseAuth
import com.wing.tree.sid.sliding.puzzle.model.Rank
import com.wing.tree.sid.sliding.puzzle.model.RankingParameter
import com.wing.tree.sid.sliding.puzzle.viewState.ResultViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val checkRankingCriteriaUseCase: CheckRankingCriteriaUseCase,
    private val firebaseAuth: FirebaseAuth,
    private val registerForRankingUseCase: RegisterForRankingUseCase,
    private val resetPuzzleUseCase: ResetPuzzleUseCase,
    private val sizeMapper: SizeMapper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _viewState = MutableStateFlow<ResultViewState>(ResultViewState.Loading)
    val viewState: StateFlow<ResultViewState> get() = _viewState

    private val ioDispatcher = Dispatchers.IO
    private val rankingParameter = savedStateHandle.get<RankingParameter>(RankingParameter.KEY)

    private var uid = EMPTY

    init {
        viewModelScope.launch(ioDispatcher) {
            rankingParameter?.size?.let {
                resetPuzzleUseCase(sizeMapper.toEntity(it))
            }
        }

        viewModelScope.launch(ioDispatcher) {
            firebaseAuth
                .signInAnonymously()
                .onSuccess {
                    uid = it

                    checkRankingCriteria()
                }.onFailure { exception ->
                    rankingParameter?.let { rankingParameter ->
                        _viewState.update {
                            ResultViewState.Content.NotRanked(rankingParameter, exception)
                        }
                    } ?: _viewState.update {
                        val s = "rankingParameter:$rankingParameter"
                        val cause = NullPointerException(s)

                        ResultViewState.Error(cause)
                    }
                }
        }
    }

    private suspend fun checkRankingCriteria() {
        rankingParameter?.let { rankingParameter ->
            val result = checkRankingCriteriaUseCase(rankingParameter).propagate()

            result.onSuccess { rank ->
                _viewState.update {
                    ResultViewState.Content.Ranked(
                        rankingParameter = rankingParameter,
                        rank = Rank(rank.inc())
                    )
                }
            }.onFailure { exception ->
                _viewState.update {
                    ResultViewState.Content.NotRanked(
                        rankingParameter = rankingParameter,
                        cause = exception
                    )
                }
            }
        } ?: _viewState.update {
            val s = "rankingParameter:null"
            val cause = NullPointerException(s)

            ResultViewState.Error(cause)
        }
    }

    suspend fun registerForRanking(nickname: Nickname): Result<Unit> {
        checkNotNull(rankingParameter)
        require(nickname.isNotBlank())

        val ranking = object : Ranking {
            override val uid = this@ResultViewModel.uid
            override val nickname = nickname.trim()
            override val playTime = rankingParameter.playTime
            override val size = rankingParameter.size
            override val timestamp = Date()
        }

        return withContext(ioDispatcher) {
            when (val result = registerForRankingUseCase(ranking)) {
                Loading -> Result.failure(IllegalStateException("result:$result"))
                is Complete.Success -> result.data
                is Complete.Failure -> Result.failure(result.cause)
            }
        }
    }
}
