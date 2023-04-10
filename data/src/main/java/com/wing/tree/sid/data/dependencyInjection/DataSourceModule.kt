package com.wing.tree.sid.data.dependencyInjection

import com.wing.tree.sid.data.source.PuzzleDataSource
import com.wing.tree.sid.data.source.PuzzleDataSourceImpl
import com.wing.tree.sid.data.source.RankingDataSource
import com.wing.tree.sid.data.source.RankingDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsPuzzleDataSource(puzzleDataSource: PuzzleDataSourceImpl): PuzzleDataSource

    @Binds
    @Singleton
    abstract fun bindsRankingDataSource(rankingDataSource: RankingDataSourceImpl): RankingDataSource
}
