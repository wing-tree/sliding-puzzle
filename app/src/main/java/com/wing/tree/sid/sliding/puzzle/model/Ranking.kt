package com.wing.tree.sid.sliding.puzzle.model

import com.wing.tree.sid.domain.entity.Nickname
import com.wing.tree.sid.domain.entity.Ranking
import java.util.*

data class Ranking(
    override val nickname: Nickname,
    override val playTime: Long,
    override val size: Int,
    override val timestamp: Date,
    override val uid: String,
    val rank: Rank
) : Ranking
