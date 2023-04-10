package com.wing.tree.sid.sliding.puzzle.model

import com.wing.tree.sid.domain.entity.Puzzle
import com.wing.tree.sid.domain.entity.Size

class Puzzle(
    override val playTime: Long,
    override val sequence: List<Int>,
    override val size: Size
) : Puzzle {
    val tiles: List<Tile> get() = with(sequence) {
        map { index ->
            if (index == max()) {
                Tile.Unoccupied(index)
            } else {
                Tile.Occupied(index)
            }
        }
    }
}