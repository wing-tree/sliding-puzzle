package com.wing.tree.sid.sliding.puzzle.extension

import com.wing.tree.sid.core.extension.isOrdered
import com.wing.tree.sid.sliding.puzzle.model.Tile

val List<Tile>.isOrdered: Boolean get() = map {
    it.index
}.isOrdered
