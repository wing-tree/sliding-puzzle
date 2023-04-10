package com.wing.tree.sid.data.mapper

internal interface EntityMapper<E, DM> {
    fun toDataModel(entity: E): DM
}
