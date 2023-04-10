package com.wing.tree.sid.domain.useCase

import com.wing.tree.sid.core.useCase.CoroutineUseCase
import com.wing.tree.sid.core.useCase.IOCoroutineDispatcher
import com.wing.tree.sid.domain.entity.Ranking
import com.wing.tree.sid.domain.repository.RankingRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class FetchRankingListUseCase @Inject constructor(
    private val rankingRepository: RankingRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<FetchRankingListUseCase.Parameter, Result<List<Ranking>>>(coroutineDispatcher) {
    override suspend fun execute(parameter: Parameter): Result<List<Ranking>> {
        return rankingRepository.fetch(parameter.page, parameter.pageSize)
    }

    data class Parameter(val page: Int, val pageSize: Int)
}
