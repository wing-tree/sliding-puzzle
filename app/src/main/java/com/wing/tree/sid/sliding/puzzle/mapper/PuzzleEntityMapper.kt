package com.wing.tree.sid.sliding.puzzle.mapper

import com.wing.tree.sid.domain.entity.Puzzle as Entity
import com.wing.tree.sid.sliding.puzzle.model.Puzzle as Model

class PuzzleEntityMapper : EntityMapper<Entity, Model> {
    override fun toModel(entity: Entity): Model {
        return Model(
            playTime = entity.playTime,
            sequence = entity.sequence.toList(),
            size = entity.size
        )
    }
}
