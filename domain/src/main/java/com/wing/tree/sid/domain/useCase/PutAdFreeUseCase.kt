package com.wing.tree.sid.domain.useCase

import com.wing.tree.sid.core.useCase.CoroutineUseCase
import com.wing.tree.sid.core.useCase.IOCoroutineDispatcher
import com.wing.tree.sid.domain.repository.PreferencesRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class PutAdFreeUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
    @IOCoroutineDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<Boolean, Unit>(coroutineDispatcher) {
    override suspend fun execute(parameter: Boolean) {
        preferencesRepository.putAdFree(parameter)
    }
}
