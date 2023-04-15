package com.wing.tree.sid.sliding.puzzle.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wing.tree.sid.core.constant.TEN
import com.wing.tree.sid.core.constant.ZERO
import com.wing.tree.sid.core.extension.long
import com.wing.tree.sid.core.extension.milliseconds
import com.wing.tree.sid.core.useCase.Result
import com.wing.tree.sid.core.useCase.map
import com.wing.tree.sid.domain.entity.Size
import com.wing.tree.sid.domain.useCase.LoadOrCreatePuzzleUseCase
import com.wing.tree.sid.domain.useCase.ResetPuzzleUseCase
import com.wing.tree.sid.domain.useCase.SavePuzzleUseCase
import com.wing.tree.sid.sliding.puzzle.mapper.PuzzleEntityMapper
import com.wing.tree.sid.sliding.puzzle.model.Puzzle
import com.wing.tree.sid.sliding.puzzle.model.RankingParameter
import com.wing.tree.sid.sliding.puzzle.view.Stopwatch
import com.wing.tree.sid.sliding.puzzle.viewState.PlayViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayViewModel @Inject constructor(
    loadOrCreatePuzzleUseCase: LoadOrCreatePuzzleUseCase,
    private val puzzleEntityMapper: PuzzleEntityMapper,
    private val resetPuzzleUseCase: ResetPuzzleUseCase,
    private val savePuzzleUseCase: SavePuzzleUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val ioDispatcher = Dispatchers.IO
    private val isSolved = MutableStateFlow(false)
    private val size = savedStateHandle.get<Size>(Size.KEY) ?: Size.default
    private val puzzle = loadOrCreatePuzzleUseCase(size)
        .map { result ->
            result.map { entity ->
                puzzleEntityMapper.toModel(entity)
            }
        }

    val playTime = MutableStateFlow(ZERO.long)
    val rankingParameter: RankingParameter
        get() = RankingParameter(
            playTime = playTime.value,
            size = size.int
        )

    val viewState: StateFlow<PlayViewState> = combine(puzzle, isSolved) { result, isSolved ->
        if (isSolved) {
            PlayViewState.Content.Solved
        } else {
            when (result) {
                Result.Loading -> PlayViewState.Loading
                is Result.Complete -> when (result) {
                    is Result.Complete.Success -> {
                        val data = result.data

                        playTime.update {
                            data.playTime
                        }

                        PlayViewState.Content.Playing(result.data)
                    }

                    is Result.Complete.Failure -> PlayViewState.Error(result.cause)
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = PlayViewState.Loading
    )

    val stopwatch = Stopwatch(
        period = TEN.milliseconds,
        callback = object : Stopwatch.Callback {
            override fun onTick(period: Long) {
                playTime.update {
                    it.plus(period)
                }
            }
        }
    )

    fun onSolved() {
        isSolved.update {
            true
        }
    }

    fun reset() {
        if (isSolved.value.not()) {
            viewModelScope.launch {
                stopwatch.reset()
                resetPuzzleUseCase(size)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun savePuzzle(sequence: List<Int>) {
        val puzzle = Puzzle(
            playTime = playTime.value,
            sequence = sequence,
            size = size
        )

        GlobalScope.launch(ioDispatcher) {
            savePuzzleUseCase(puzzle)
        }
    }
}
