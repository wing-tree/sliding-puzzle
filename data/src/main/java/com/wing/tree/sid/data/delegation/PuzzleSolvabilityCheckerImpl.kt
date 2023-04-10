package com.wing.tree.sid.data.delegation

import com.wing.tree.sid.core.constant.ZERO
import com.wing.tree.sid.core.extension.isEven
import com.wing.tree.sid.core.extension.isOdd
import com.wing.tree.sid.data.model.Puzzle

class PuzzleSolvabilityCheckerImpl : PuzzleSolvabilityChecker {
    override fun checkSolvability(puzzle: Puzzle): Boolean {
        val column = puzzle.size.column
        val inversionCount = puzzle.inversionCount
        val sequence = puzzle.sequence

        if (puzzle.isOrdered) {
            return false
        }

        return if (column.isOdd) {
            inversionCount.isEven
        } else {
            val indices = sequence.indices
            val index = indices.maxBy { sequence[it] }

            if (index.div(column).isEven) {
                inversionCount.isOdd
            } else {
                inversionCount.isEven
            }
        }
    }

    private val Puzzle.inversionCount: Int
        get() = with(sequence) {
            var inversionCount = ZERO

            repeat(size.dec()) { i ->
                for (j in i.inc() until size) {
                    when {
                        get(i) == max() -> continue
                        get(j) == max() -> continue
                        get(i) > get(j) -> inversionCount++
                    }
                }
            }

            return inversionCount
        }
}
