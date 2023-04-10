package com.wing.tree.sid.data.dependencyInjection

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.wing.tree.sid.data.store.proto.Map
import com.wing.tree.sid.data.store.proto.MapSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ProtoDataStoreModule {
    @Singleton
    @Provides
    fun providesMapDataStore(@ApplicationContext context: Context): DataStore<Map> {
        return DataStoreFactory.create(
            serializer = MapSerializer,
            produceFile = {
                context.dataStoreFile("map.pb")
            },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }
}
