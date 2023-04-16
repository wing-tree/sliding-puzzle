package com.wing.tree.sid.domain.useCase

import com.wing.tree.sid.core.useCase.IOCoroutineDispatcher
import com.wing.tree.sid.core.useCase.NoParameterFlowUseCase
import com.wing.tree.sid.domain.repository.PreferencesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFirstLaunchedAtUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher
) : NoParameterFlowUseCase<Long?>(coroutineDispatcher) {
    override fun execute(): Flow<Long?> {
        return preferencesRepository.getFirstLaunchedAt()
    }
}
