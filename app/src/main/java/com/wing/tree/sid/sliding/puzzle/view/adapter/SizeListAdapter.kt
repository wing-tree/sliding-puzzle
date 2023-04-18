package com.wing.tree.sid.sliding.puzzle.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wing.tree.sid.core.extension.`is`
import com.wing.tree.sid.domain.entity.Size
import com.wing.tree.sid.sliding.puzzle.R
import com.wing.tree.sid.sliding.puzzle.databinding.SizeBinding
import com.wing.tree.sid.sliding.puzzle.extension.context
import com.wing.tree.sid.sliding.puzzle.extension.setOnClickListener
import com.wing.tree.sid.sliding.puzzle.extension.string

class SizeListAdapter(
    private val onItemClickListener: OnItemClickListener
) : ListAdapter<Size, SizeListAdapter.ViewHolder>(ItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = SizeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun interface OnItemClickListener {
        fun onItemClick(item: Size)
    }

    inner class ViewHolder(private val viewBinding: SizeBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: Size) {
            with(viewBinding) {
                setOnClickListener {
                    onItemClickListener.onItemClick(item)
                }

                text.text = item.toText(context)
            }
        }
    }

    private fun Size.toText(context: Context): String =
        "$int-${context.string(R.string.puzzle)}"

    private class ItemCallback : DiffUtil.ItemCallback<Size>() {
        override fun areItemsTheSame(oldItem: Size, newItem: Size): Boolean {
            return oldItem `is` newItem
        }

        override fun areContentsTheSame(oldItem: Size, newItem: Size): Boolean {
            return oldItem `is` newItem
        }
    }
}
