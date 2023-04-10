package com.wing.tree.sid.data.mapper

import javax.inject.Inject
import com.wing.tree.sid.data.model.Puzzle as DataModel
import com.wing.tree.sid.data.store.proto.Puzzle as Proto

class PuzzleProtoMapper @Inject constructor(
    private val sizeMapper: SizeMapper
) : ProtoMapper<Proto, DataModel> {
    override fun toDataModel(proto: Proto): DataModel {
        return DataModel(
            playTime = proto.playTime,
            sequence = proto.sequenceList,
            size = sizeMapper.toEntity(proto.size)
        )
    }

    override fun toProto(dataModel: DataModel): Proto {
        return Proto.newBuilder()
            .addAllSequence(dataModel.sequence.toList())
            .setPlayTime(dataModel.playTime)
            .setSize(dataModel.size.int)
            .build()
    }
}
