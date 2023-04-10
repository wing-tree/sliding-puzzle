package com.wing.tree.sid.data.repository

import com.wing.tree.sid.data.mapper.RankingMapper
import com.wing.tree.sid.data.mapper.RankingParameterMapper
import com.wing.tree.sid.data.source.RankingDataSource
import com.wing.tree.sid.domain.entity.Ranking
import com.wing.tree.sid.domain.entity.RankingParameter
import com.wing.tree.sid.domain.repository.RankingRepository
import javax.inject.Inject

class RankingRepositoryImpl @Inject constructor(
    private val rankingDataSource: RankingDataSource,
    private val rankingMapper: RankingMapper,
    private val rankingParameterMapper: RankingParameterMapper
) : RankingRepository {
    override suspend fun checkCriteria(rankingParameter: RankingParameter): Result<Int> {
        return rankingDataSource.checkCriteria(rankingParameterMapper.toDataModel(rankingParameter))
    }

    override suspend fun fetch(page: Int, pageSize: Int): Result<List<Ranking>> {
        return rankingDataSource.fetch(page, pageSize).map {
            it.map { dataModel -> rankingMapper.toEntity(dataModel) }
        }
    }

    override suspend fun register(ranking: Ranking): Result<Unit> {
        return rankingDataSource.register(rankingMapper.toDataModel(ranking))
    }
}
