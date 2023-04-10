package com.wing.tree.sid.sliding.puzzle.mapper

import com.wing.tree.sid.sliding.puzzle.model.Rank
import com.wing.tree.sid.sliding.puzzle.model.Ranking as Model
import com.wing.tree.sid.domain.entity.Ranking as Entity

class RankingMapper {
    fun toModel(rank: Rank, entity: Entity): Model {
        return Model(
            nickname = entity.nickname,
            playTime = entity.playTime,
            size = entity.size,
            timestamp = entity.timestamp,
            uid = entity.uid,
            rank = rank
        )
    }
}
