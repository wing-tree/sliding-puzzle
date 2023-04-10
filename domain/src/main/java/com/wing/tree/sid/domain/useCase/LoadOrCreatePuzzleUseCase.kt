package com.wing.tree.sid.domain.useCase

import com.wing.tree.sid.core.useCase.DefaultCoroutineDispatcher
import com.wing.tree.sid.core.useCase.FlowUseCase
import com.wing.tree.sid.domain.entity.Puzzle
import com.wing.tree.sid.domain.entity.Size
import com.wing.tree.sid.domain.repository.PuzzleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadOrCreatePuzzleUseCase @Inject constructor(
    private val puzzleRepository: PuzzleRepository,
    @DefaultCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher
) : FlowUseCase<Size, Puzzle>(coroutineDispatcher) {
    override fun execute(parameter: Size): Flow<Puzzle> {
        return puzzleRepository.loadOrCreate(parameter)
    }
}
