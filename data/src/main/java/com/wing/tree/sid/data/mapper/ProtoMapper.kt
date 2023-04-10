package com.wing.tree.sid.data.mapper

internal interface ProtoMapper<P, DM> {
    fun toDataModel(proto: P): DM
    fun toProto(dataModel: DM): P
}
