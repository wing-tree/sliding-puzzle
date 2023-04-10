package com.wing.tree.sid.domain.entity

import java.util.Date

interface Ranking {
    val nickname: Nickname
    val playTime: Long
    val size: Int
    val timestamp: Date
    val uid: String
}
