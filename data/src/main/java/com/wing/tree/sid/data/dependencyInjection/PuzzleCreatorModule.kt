package com.wing.tree.sid.data.dependencyInjection

import com.wing.tree.sid.data.mapper.SizeMapper
import com.wing.tree.sid.data.source.PuzzleCreator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PuzzleCreatorModule {
    @Singleton
    @Provides
    fun providesPuzzleCreator(sizeMapper: SizeMapper): PuzzleCreator {
        return PuzzleCreator(sizeMapper)
    }
}
