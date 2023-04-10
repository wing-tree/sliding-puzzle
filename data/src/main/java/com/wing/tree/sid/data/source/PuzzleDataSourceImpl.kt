package com.wing.tree.sid.data.source

import androidx.datastore.core.DataStore
import com.wing.tree.sid.data.mapper.PuzzleProtoMapper
import com.wing.tree.sid.data.model.Puzzle
import com.wing.tree.sid.data.store.proto.Map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PuzzleDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Map>,
    private val puzzleCreator: PuzzleCreator,
    private val puzzleProtoMapper: PuzzleProtoMapper
) : PuzzleDataSource {
    override fun loadOrCreate(size: Int): Flow<Puzzle> {
        return dataStore.data.map { map ->
            map.puzzleMap[size]?.let {
                puzzleProtoMapper.toDataModel(it)
            } ?: puzzleCreator.create(size)
        }
    }

    override suspend fun reset(size: Int) {
        dataStore.updateData {
            val puzzle = puzzleCreator.create(size)
            val proto = puzzleProtoMapper.toProto(puzzle)

            it.toBuilder()
                .putPuzzle(size, proto)
                .build()
        }
    }

    override suspend fun save(puzzle: Puzzle) {
        dataStore.updateData {
            val builder = it.toBuilder()
            val key = puzzle.size.int
            val value = puzzleProtoMapper.toProto(puzzle)

            builder
                .putPuzzle(key, value)
                .build()
        }
    }
}
