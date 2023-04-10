package com.wing.tree.sid.data.mapper

import com.wing.tree.sid.data.model.Puzzle as DataModel
import com.wing.tree.sid.domain.entity.Puzzle as Entity

class PuzzleEntityMapper : EntityMapper<Entity, DataModel> {
    override fun toDataModel(entity: Entity): DataModel {
        return DataModel(
            playTime = entity.playTime,
            sequence = entity.sequence.toList(),
            size = entity.size
        )
    }
}
