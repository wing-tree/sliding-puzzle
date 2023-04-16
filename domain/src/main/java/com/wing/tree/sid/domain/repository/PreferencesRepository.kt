package com.wing.tree.sid.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getFirstLaunchedAt(): Flow<Long?>
    fun isAdFreePurchased(): Flow<Boolean>
    suspend fun putAdFreePurchased(isAdFree: Boolean)
    suspend fun putFirstLaunchedAt(firstLaunchedAt: Long)
}
