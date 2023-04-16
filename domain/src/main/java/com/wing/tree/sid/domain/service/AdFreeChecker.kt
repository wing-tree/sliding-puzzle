package com.wing.tree.sid.domain.service

import com.wing.tree.sid.core.constant.AD_FREE_GRACE_PERIOD_IN_HOURS
import com.wing.tree.sid.core.useCase.IOCoroutineDispatcher
import com.wing.tree.sid.core.useCase.getOrDefault
import com.wing.tree.sid.domain.useCase.GetFirstLaunchedAtUseCase
import com.wing.tree.sid.domain.useCase.IsAdFreePurchasedUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AdFreeChecker @Inject constructor(
    @IOCoroutineDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val isAdFreePurchasedUseCase: IsAdFreePurchasedUseCase,
    private val getFirstLaunchedAtUseCase: GetFirstLaunchedAtUseCase,
) {
    suspend operator fun invoke(): Boolean {
        return withContext(coroutineDispatcher) {
            combine(
                isAdFreePurchasedUseCase(),
                getFirstLaunchedAtUseCase(),
            ) { isAdFreePurchased, firstLaunchedAt ->
                if (isAdFreePurchased.getOrDefault(false)) {
                    true
                } else {
                    with(firstLaunchedAt.getOrDefault(Long.MAX_VALUE)) {
                        val currentTimeMillis = System.currentTimeMillis()
                        val duration = currentTimeMillis.minus(this)

                        TimeUnit.MILLISECONDS.toHours(duration) < AD_FREE_GRACE_PERIOD_IN_HOURS
                    }
                }
            }
                .first()
        }
    }
}
