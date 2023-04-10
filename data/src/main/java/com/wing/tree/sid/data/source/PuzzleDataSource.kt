package com.wing.tree.sid.data.source

import com.wing.tree.sid.data.model.Puzzle
import kotlinx.coroutines.flow.Flow

interface PuzzleDataSource {
    fun loadOrCreate(size: Int): Flow<Puzzle>
    suspend fun reset(size: Int)
    suspend fun save(puzzle: Puzzle)
}
