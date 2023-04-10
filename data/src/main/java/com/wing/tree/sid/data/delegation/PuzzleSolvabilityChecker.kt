package com.wing.tree.sid.data.delegation

import com.wing.tree.sid.data.model.Puzzle

interface PuzzleSolvabilityChecker {
    fun checkSolvability(puzzle: Puzzle): Boolean
}
