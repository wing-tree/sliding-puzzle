package com.wing.tree.sid.domain.useCase

import com.wing.tree.sid.core.useCase.CoroutineUseCase
import com.wing.tree.sid.core.useCase.DefaultCoroutineDispatcher
import com.wing.tree.sid.domain.entity.RankingParameter
import com.wing.tree.sid.domain.repository.RankingRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class CheckRankingCriteriaUseCase @Inject constructor(
    private val rankingRepository: RankingRepository,
    @DefaultCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<RankingParameter, Result<Int>>(coroutineDispatcher) {
    override suspend fun execute(parameter: RankingParameter): Result<Int> {
        return rankingRepository.checkCriteria(parameter)
    }
}
