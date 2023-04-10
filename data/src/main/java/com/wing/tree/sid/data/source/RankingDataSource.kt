package com.wing.tree.sid.data.source

import com.wing.tree.sid.data.model.Ranking
import com.wing.tree.sid.data.model.RankingParameter

interface RankingDataSource {
    suspend fun checkCriteria(rankingParameter: RankingParameter): Result<Int>
    suspend fun fetch(page: Int, pageSize: Int): Result<List<Ranking>>
    suspend fun register(ranking: Ranking): Result<Unit>
}
