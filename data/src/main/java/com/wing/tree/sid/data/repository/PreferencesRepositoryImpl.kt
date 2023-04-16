package com.wing.tree.sid.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.wing.tree.sid.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {
    private object Name {
        private const val OBJECT_NAME = "Name"
        const val IS_AD_FREE = "$OBJECT_NAME.IS_AD_FREE"
        const val FIRST_LAUNCHED_AT = "$OBJECT_NAME.FIRST_LAUNCHED_AT"
    }

    private object Key {
        val isAdFree = booleanPreferencesKey(Name.IS_AD_FREE)
        val firstLaunchedAt = longPreferencesKey(Name.FIRST_LAUNCHED_AT)
    }

    override fun isAdFree(): Flow<Boolean> {
        return dataStore.data.map {
            it[Key.isAdFree] ?: false
        }
    }

    override fun getFirstLaunchedAt(): Flow<Long?> {
        return dataStore.data.map {
            it[Key.firstLaunchedAt]
        }
    }

    override suspend fun putAdFree(isAdFree: Boolean) {
        dataStore.edit {
            it[Key.isAdFree] = isAdFree
        }
    }

    override suspend fun putFirstLaunchedAt(firstLaunchedAt: Long) {
        dataStore.edit {
            it[Key.firstLaunchedAt] = firstLaunchedAt
        }
    }
}