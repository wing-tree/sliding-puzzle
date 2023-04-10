package com.wing.tree.sid.domain.repository

import com.wing.tree.sid.domain.entity.Puzzle
import com.wing.tree.sid.domain.entity.Size
import kotlinx.coroutines.flow.Flow

interface PuzzleRepository {
    fun loadOrCreate(size: Size): Flow<Puzzle>
    suspend fun reset(size: Size)
    suspend fun save(puzzle: Puzzle)
}
