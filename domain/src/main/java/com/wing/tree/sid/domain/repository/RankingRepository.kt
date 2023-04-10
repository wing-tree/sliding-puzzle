package com.wing.tree.sid.domain.repository

import com.wing.tree.sid.domain.entity.Ranking
import com.wing.tree.sid.domain.entity.RankingParameter

interface RankingRepository {
    suspend fun checkCriteria(rankingParameter: RankingParameter): Result<Int>
    suspend fun fetch(page: Int, pageSize: Int): Result<List<Ranking>>
    suspend fun register(ranking: Ranking): Result<Unit>
}
