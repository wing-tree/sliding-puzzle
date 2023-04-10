package com.wing.tree.sid.data.mapper

import com.wing.tree.sid.data.model.RankingParameter as DataModel
import com.wing.tree.sid.domain.entity.RankingParameter as Entity

class RankingParameterMapper : EntityMapper<Entity, DataModel> {
    override fun toDataModel(entity: Entity): DataModel {
        return DataModel(
            playTime = entity.playTime,
            size = entity.size
        )
    }
}
