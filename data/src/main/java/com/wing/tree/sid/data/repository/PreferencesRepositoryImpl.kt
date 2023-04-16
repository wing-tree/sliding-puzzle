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
        const val IS_AD_FREE_PURCHASED = "$OBJECT_NAME.IS_AD_FREE"
        const val FIRST_LAUNCHED_AT = "$OBJECT_NAME.FIRST_LAUNCHED_AT"
    }

    private object Key {
        val isAdFreePurchased = booleanPreferencesKey(Name.IS_AD_FREE_PURCHASED)
        val firstLaunchedAt = longPreferencesKey(Name.FIRST_LAUNCHED_AT)
    }

    override fun isAdFreePurchased(): Flow<Boolean> {
        return dataStore.data.map {
            it[Key.isAdFreePurchased] ?: false
        }
    }

    override fun getFirstLaunchedAt(): Flow<Long?> {
        return dataStore.data.map {
            it[Key.firstLaunchedAt]
        }
    }

    override suspend fun putAdFreePurchased(isAdFree: Boolean) {
        dataStore.edit {
            it[Key.isAdFreePurchased] = isAdFree
        }
    }

    override suspend fun putFirstLaunchedAt(firstLaunchedAt: Long) {
        dataStore.edit {
            it[Key.firstLaunchedAt] = firstLaunchedAt
        }
    }
}