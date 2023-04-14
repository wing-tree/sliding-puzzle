package com.wing.tree.sid.sliding.puzzle.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.wing.tree.sid.core.constant.ZERO
import com.wing.tree.sid.core.extension.*
import com.wing.tree.sid.domain.entity.Size
import com.wing.tree.sid.sliding.puzzle.R
import com.wing.tree.sid.sliding.puzzle.databinding.TileBinding
import com.wing.tree.sid.sliding.puzzle.extension.*
import com.wing.tree.sid.sliding.puzzle.model.Tile
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class TileListAdapter(
    private val size: Size,
    private val listener: Listener
) : RecyclerView.Adapter<TileListAdapter.ViewHolder>() {
    private val _currentList = mutableListOf<Tile>()
    val currentList: List<Tile> get() = _currentList

    private val callback = Callback(currentList)
    private val isDraggable = AtomicBoolean(true)
    private val isSolved = AtomicBoolean(false)
    private val unoccupied = Unoccupied()

    val sequence: List<Int> get() = currentList.map { it.index }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = TileBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).apply {
            with(index) {
                setTextAppearance(size.textAppearance)
            }
        }

        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.count()

    private fun onMoved(fromPos: Int, toPos: Int) {
        notifyItemChanged(fromPos)
        notifyItemChanged(toPos)
        listener.onMoved(currentList.toList())
    }

    fun submitList(list: List<Tile>) {
        val diffResult = DiffUtil.calculateDiff(
            callback.apply {
                updateNewList {
                    list
                }
            }
        )

        _currentList.clearAndAddAll(list)

        diffResult.dispatchUpdatesTo(this)
    }

    fun onSolved() {
        isSolved.set(false)
    }

    private val Size.textAppearance: Int get() = when(this) {
        Size.Eight -> R.style.DisplayMedium
        Size.Fifteen -> R.style.DisplaySmall
        Size.TwentyFour -> R.style.HeadlineLarge
        Size.ThirtyFive -> R.style.HeadlineMedium
    }

    fun interface Listener {
        fun onMoved(currentList: List<Tile>)
    }

    private enum class Action {
        Click, Fling
    }

    private inner class Unoccupied {
        val index: Int get() = currentList.indexOfFirst<Tile.Unoccupied>()
    }

    inner class ViewHolder(private val viewBinding: TileBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        private val holder = this
        private val root = viewBinding.root

        fun bind(item: Tile) {
            with(viewBinding) {
                when (item) {
                    is Tile.Occupied -> {
                        visible()

                        index.setDongleText("${item.index}")

                        setOnClickListener {
                            val isDraggable = isDraggable.get()
                            if (isDraggable) {
                                move(holder, Action.Click)
                            }
                        }

                        setOnTouchListener(
                            object : OnFlingListener(context) {
                                override fun onFling(direction: Direction): Boolean {
                                    return if (direction.dragFlag == holder.dragFlag) {
                                        move(holder, Action.Fling)
                                        false
                                    } else {
                                        true
                                    }
                                }
                            }
                        )
                    }

                    is Tile.Unoccupied -> gone()
                }
            }
        }

        val dragFlag: Int get() = run {
            val up = unoccupied.index.minus(size.row)
            val down = unoccupied.index.plus(size.row)
            val left = unoccupied.index.dec()
            val right = unoccupied.index.inc()

            return when(adapterPosition) {
                up -> ItemTouchHelper.DOWN
                down -> ItemTouchHelper.UP
                left -> when {
                    unoccupied.index.rem(size.column).isZero -> ZERO
                    else -> ItemTouchHelper.RIGHT
                }
                right -> when {
                    unoccupied.index.inc().rem(size.column).isZero -> ZERO
                    else -> ItemTouchHelper.LEFT
                }
                else -> ZERO
            }
        }

        private fun move(holder: ViewHolder, action: Action) {
            val dragFlag = holder.dragFlag

            if (dragFlag.isZero) {
                return
            }

            val context = holder.root.context
            val fromPos = holder.adapterPosition
            val toPos = unoccupied.index

            val duration = with(context) {
                when (action) {
                    Action.Click -> integer(R.integer.slide_duration_medium)
                    Action.Fling -> integer(R.integer.slide_duration_short)
                }.long
            }

            isDraggable.set(false)

            with(holder.root) {
                when(dragFlag) {
                    ItemTouchHelper.UP -> slideUp(duration)
                    ItemTouchHelper.DOWN -> slideDown(duration)
                    ItemTouchHelper.LEFT ->  slideLeft(duration)
                    ItemTouchHelper.RIGHT -> slideRight(duration)
                    else -> throw IllegalArgumentException("$dragFlag")
                }.doOnEnd {
                    Collections.swap(_currentList, fromPos, toPos)

                    translationX = ZERO.float
                    translationY = ZERO.float

                    onMoved(fromPos, toPos)

                    val isSolved = isSolved.get()

                    if (isSolved.not()) {
                        isDraggable.set(true)
                    }
                }
            }
        }
    }
}
