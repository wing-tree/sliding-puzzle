package com.wing.tree.sid.sliding.puzzle.mapper

interface EntityMapper<E, M> {
    fun toModel(entity: E): M
}
