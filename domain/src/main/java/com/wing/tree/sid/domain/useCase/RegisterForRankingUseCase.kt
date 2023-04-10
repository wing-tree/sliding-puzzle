package com.wing.tree.sid.domain.useCase

import com.wing.tree.sid.core.useCase.CoroutineUseCase
import com.wing.tree.sid.core.useCase.IOCoroutineDispatcher
import com.wing.tree.sid.domain.entity.Ranking
import com.wing.tree.sid.domain.repository.RankingRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RegisterForRankingUseCase @Inject constructor(
    private val rankingRepository: RankingRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<Ranking, Result<Unit>>(coroutineDispatcher) {
    override suspend fun execute(parameter: Ranking): Result<Unit> {
        return rankingRepository.register(parameter)
    }
}