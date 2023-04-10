package com.wing.tree.sid.sliding.puzzle.model

import com.wing.tree.sid.domain.entity.Tile as Entity

sealed interface Tile : Entity {
    data class Occupied(override val index: Int) : Tile
    data class Unoccupied(override val index: Int) : Tile
}
