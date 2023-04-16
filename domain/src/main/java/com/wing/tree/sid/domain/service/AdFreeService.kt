package com.wing.tree.sid.domain.service

import com.wing.tree.sid.domain.useCase.IsAdFreeUseCase
import com.wing.tree.sid.domain.useCase.PutAdFreeUseCase
import javax.inject.Inject

class AdFreeService @Inject constructor(
    private val isAdFreeUseCase: IsAdFreeUseCase,
    private val putAdFreeUseCase: PutAdFreeUseCase,
) {
    fun `is`() = isAdFreeUseCase()
    suspend fun put(isAdFree: Boolean) = putAdFreeUseCase(isAdFree)
}
