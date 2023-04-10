package com.wing.tree.sid.data.repository

import com.wing.tree.sid.data.mapper.PuzzleEntityMapper
import com.wing.tree.sid.data.mapper.SizeMapper
import com.wing.tree.sid.data.source.PuzzleDataSource
import com.wing.tree.sid.domain.entity.Puzzle
import com.wing.tree.sid.domain.entity.Size
import com.wing.tree.sid.domain.repository.PuzzleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PuzzleRepositoryImpl @Inject constructor(
    private val puzzleEntityMapper: PuzzleEntityMapper,
    private val puzzleDataSource: PuzzleDataSource,
    private val sizeMapper: SizeMapper
) : PuzzleRepository {
    override fun loadOrCreate(size: Size): Flow<Puzzle> {
        return puzzleDataSource.loadOrCreate(size.int)
    }

    override suspend fun reset(size: Size) {
        puzzleDataSource.reset(size.int)
    }

    override suspend fun save(puzzle: Puzzle) {
        puzzleDataSource.save(puzzleEntityMapper.toDataModel(puzzle))
    }
}
