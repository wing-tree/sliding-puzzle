package com.wing.tree.sid.sliding.puzzle.dependencyInjection

import com.wing.tree.sid.sliding.puzzle.firebase.FirebaseAuth
import com.wing.tree.sid.sliding.puzzle.mapper.PuzzleEntityMapper
import com.wing.tree.sid.sliding.puzzle.mapper.RankingMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FirebaseAuthModule {
    @Singleton
    @Provides
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth()
    }
}
