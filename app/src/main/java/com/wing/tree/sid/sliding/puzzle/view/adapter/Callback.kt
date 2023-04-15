package com.wing.tree.sid.sliding.puzzle.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.wing.tree.sid.core.extension.`is`
import com.wing.tree.sid.sliding.puzzle.model.Tile

class Callback(
    private val oldList: List<Tile>
) : DiffUtil.Callback() {
    var newList = listOf<Tile>()
        private set

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.index.`is`(newItem.index)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.`is`(newItem)
    }

    fun updateNewList(block: List<Tile>.() -> List<Tile>) {
        newList = block(newList)
    }
}
