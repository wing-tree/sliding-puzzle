package com.wing.tree.sid.data.mapper

internal interface Mapper<E, DM>: EntityMapper<E, DM>, DataModelMapper<DM, E>
