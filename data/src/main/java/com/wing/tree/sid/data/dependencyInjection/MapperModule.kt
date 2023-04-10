package com.wing.tree.sid.data.dependencyInjection

import com.wing.tree.sid.data.mapper.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MapperModule {
    @Singleton
    @Provides
    fun providesPuzzleEntityMapper(): PuzzleEntityMapper {
        return PuzzleEntityMapper()
    }

    @Singleton
    @Provides
    fun providesPuzzleProtoMapper(sizeMapper: SizeMapper): PuzzleProtoMapper {
        return PuzzleProtoMapper(sizeMapper)
    }

    @Singleton
    @Provides
    fun providesRankingMapper(): RankingMapper {
        return RankingMapper()
    }

    @Singleton
    @Provides
    fun providesRankingParameterMapper(): RankingParameterMapper {
        return RankingParameterMapper()
    }

    @Singleton
    @Provides
    fun providesSizeMapper(): SizeMapper {
        return SizeMapper()
    }
}
