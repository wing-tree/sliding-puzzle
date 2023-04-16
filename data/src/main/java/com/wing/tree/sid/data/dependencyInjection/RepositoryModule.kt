package com.wing.tree.sid.data.dependencyInjection

import com.wing.tree.sid.data.repository.PreferencesRepositoryImpl
import com.wing.tree.sid.data.repository.PuzzleRepositoryImpl
import com.wing.tree.sid.data.repository.RankingRepositoryImpl
import com.wing.tree.sid.domain.repository.PreferencesRepository
import com.wing.tree.sid.domain.repository.PuzzleRepository
import com.wing.tree.sid.domain.repository.RankingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsPreferencesRepository(
        preferencesRepository: PreferencesRepositoryImpl
    ): PreferencesRepository

    @Binds
    @Singleton
    abstract fun bindsPuzzleRepository(puzzleRepository: PuzzleRepositoryImpl): PuzzleRepository

    @Binds
    @Singleton
    abstract fun bindsRankingRepository(rankingRepository: RankingRepositoryImpl): RankingRepository
}
