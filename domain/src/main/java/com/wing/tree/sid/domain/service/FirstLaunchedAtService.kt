package com.wing.tree.sid.domain.service

import com.wing.tree.sid.core.useCase.Result
import com.wing.tree.sid.domain.useCase.GetFirstLaunchedAtUseCase
import com.wing.tree.sid.domain.useCase.PutFirstLaunchedAtUseCase
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

class FirstLaunchedAtService @Inject constructor(
    private val getFirstLaunchedAtUseCase : GetFirstLaunchedAtUseCase,
    private val putFirstLaunchedAtUseCase: PutFirstLaunchedAtUseCase,
) {
    fun get() = getFirstLaunchedAtUseCase()

    suspend fun collect(collector: FlowCollector<Result<Long?>>) = get().collect(collector)
    suspend fun put(firstLaunchedAt: Long) = putFirstLaunchedAtUseCase(firstLaunchedAt)
}
