package com.wing.tree.sid.data.store.proto

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object MapSerializer : Serializer<Map> {
    override val defaultValue: Map = Map.newBuilder()
        .clearPuzzle()
        .build()

    override suspend fun readFrom(input: InputStream): Map {
        try {
            @Suppress("BlockingMethodInNonBlockingContext")
            return Map.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun writeTo(t: Map, output: OutputStream) = t.writeTo(output)
}
