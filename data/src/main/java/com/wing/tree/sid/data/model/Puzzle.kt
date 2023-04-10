package com.wing.tree.sid.data.model

import com.wing.tree.sid.core.constant.ZERO
import com.wing.tree.sid.core.extension.isOrdered
import com.wing.tree.sid.core.extension.long
import com.wing.tree.sid.domain.entity.Puzzle
import com.wing.tree.sid.domain.entity.Size

class Puzzle(
    override val playTime: Long = ZERO.long,
    override val sequence: List<Int>,
    override val size: Size
) : Puzzle {
    val isOrdered: Boolean get() = sequence.isOrdered
}
