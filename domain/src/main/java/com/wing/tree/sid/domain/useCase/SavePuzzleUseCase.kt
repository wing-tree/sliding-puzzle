package com.wing.tree.sid.domain.useCase

import com.wing.tree.sid.core.useCase.CoroutineUseCase
import com.wing.tree.sid.core.useCase.IOCoroutineDispatcher
import com.wing.tree.sid.domain.entity.Puzzle
import com.wing.tree.sid.domain.repository.PuzzleRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SavePuzzleUseCase @Inject constructor(
    private val puzzleRepository: PuzzleRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<Puzzle, Unit>(coroutineDispatcher) {
    override suspend fun execute(parameter: Puzzle) {
        puzzleRepository.save(parameter)
    }
}
