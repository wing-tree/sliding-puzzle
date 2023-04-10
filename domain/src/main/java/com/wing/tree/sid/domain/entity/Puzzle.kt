package com.wing.tree.sid.domain.entity

interface Puzzle {
    val playTime: Long
    val sequence: List<Int>
    val size: Size
}
