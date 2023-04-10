package com.wing.tree.sid.data.mapper

import com.google.firebase.Timestamp
import com.wing.tree.sid.domain.entity.Nickname
import java.util.*
import com.wing.tree.sid.domain.entity.Ranking as Entity
import com.wing.tree.sid.data.model.Ranking as DataModel

class RankingMapper : Mapper<Entity, DataModel> {
    override fun toEntity(dataModel: DataModel): Entity {
        return object : Entity {
            override val nickname: Nickname = Nickname(dataModel.nickname)
            override val playTime: Long = dataModel.playTime
            override val size: Int = dataModel.size
            override val timestamp: Date = dataModel.timestamp.toDate()
            override val uid: String = dataModel.uid
        }
    }

    override fun toDataModel(entity: Entity): DataModel {
        return DataModel(
            nickname = "${entity.nickname}",
            playTime = entity.playTime,
            size = entity.size,
            timestamp = Timestamp(entity.timestamp),
            uid = entity.uid
        )
    }
}