package com.wing.tree.sid.domain.useCase

import com.wing.tree.sid.core.useCase.CoroutineUseCase
import com.wing.tree.sid.core.useCase.DefaultCoroutineDispatcher
import com.wing.tree.sid.domain.entity.Size
import com.wing.tree.sid.domain.repository.PuzzleRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ResetPuzzleUseCase @Inject constructor(
    private val puzzleRepository: PuzzleRepository,
    @DefaultCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<Size, Unit>(coroutineDispatcher) {
    override suspend fun execute(parameter: Size) {
        puzzleRepository.reset(parameter)
    }
}
