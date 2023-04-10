package com.wing.tree.sid.data.source

import com.wing.tree.sid.data.delegation.PuzzleSolvabilityChecker
import com.wing.tree.sid.data.delegation.PuzzleSolvabilityCheckerImpl
import com.wing.tree.sid.data.mapper.SizeMapper
import com.wing.tree.sid.data.model.Puzzle
import javax.inject.Inject

class PuzzleCreator @Inject constructor(
    private val sizeMapper: SizeMapper
) : PuzzleSolvabilityChecker by PuzzleSolvabilityCheckerImpl() {
    fun create(size: Int): Puzzle {
        val sequence = MutableList(size.inc()) {
            it.inc()
        }.apply {
            shuffle()
        }

        return Puzzle(
            sequence = sequence,
            size = sizeMapper.toEntity(size)
        ).apply {
            while (checkSolvability(this).not()) {
                sequence.shuffle()
            }
        }
    }
}
