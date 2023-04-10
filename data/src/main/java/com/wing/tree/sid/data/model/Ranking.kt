package com.wing.tree.sid.data.model

import com.google.firebase.Timestamp
import com.wing.tree.sid.core.constant.EMPTY
import com.wing.tree.sid.core.extension.not
import com.wing.tree.sid.domain.entity.RankingParameter
import com.wing.tree.sid.domain.entity.Size

data class Ranking(
    val nickname: String = EMPTY,
    val playTime: Long = Long.MAX_VALUE,
    val size: Int = Size.default.int,
    val timestamp: Timestamp = Timestamp.now(),
    val uid: String = EMPTY
) {
    fun isHigher(other: RankingParameter): Boolean {
        if (size not other.size) {
            return size < other.size
        }

        return playTime > other.playTime
    }
}
