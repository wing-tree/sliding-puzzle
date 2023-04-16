package com.wing.tree.sid.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    fun getFirstLaunchedAt(): Flow<Long?>
    fun isAdFree(): Flow<Boolean>
    suspend fun putAdFree(isAdFree: Boolean)
    suspend fun putFirstLaunchedAt(firstLaunchedAt: Long)
}
