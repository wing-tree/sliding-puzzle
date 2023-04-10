package com.wing.tree.sid.sliding.puzzle.model

import android.os.Parcelable
import com.wing.tree.sid.core.extension.not
import com.wing.tree.sid.domain.entity.RankingParameter
import kotlinx.parcelize.Parcelize

@Parcelize
data class RankingParameter(
    override val playTime: Long,
    override val size: Int
) : RankingParameter, Parcelable {
    override fun isHigher(other: RankingParameter): Boolean {
        if (size not other.size) {
            return size > other.size
        }

        return playTime < other.playTime
    }

    companion object {
        const val KEY = "rankingParameter"
    }
}