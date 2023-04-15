package com.wing.tree.sid.sliding.puzzle.view.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.wing.tree.sid.core.extension.`is`

class ItemDecoration(
    private val left: Int,
    private val right: Int,
    private val top: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = left
        outRect.top = top
        outRect.right = right

        if (parent.getChildAdapterPosition(view).`is`(state.itemCount.dec())) {
            outRect.bottom = top
        }
    }
}
