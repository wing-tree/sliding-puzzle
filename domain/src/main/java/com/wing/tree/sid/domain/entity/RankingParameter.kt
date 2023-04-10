package com.wing.tree.sid.domain.entity

interface RankingParameter {
    val playTime: Long
    val size: Int

    fun isHigher(other: RankingParameter): Boolean
}