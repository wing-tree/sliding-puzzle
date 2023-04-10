package com.wing.tree.sid.data.mapper

internal interface DataModelMapper<DM, E> {
    fun toEntity(dataModel: DM): E
}
