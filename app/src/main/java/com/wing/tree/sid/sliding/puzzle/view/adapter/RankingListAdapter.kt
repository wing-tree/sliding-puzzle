package com.wing.tree.sid.sliding.puzzle.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.wing.tree.sid.core.constant.ONE_THOUSAND
import com.wing.tree.sid.core.constant.TEN
import com.wing.tree.sid.core.extension.`is`
import com.wing.tree.sid.domain.entity.Size
import com.wing.tree.sid.sliding.puzzle.R
import com.wing.tree.sid.sliding.puzzle.databinding.RankingBinding
import com.wing.tree.sid.sliding.puzzle.extension.context
import com.wing.tree.sid.sliding.puzzle.extension.string
import com.wing.tree.sid.sliding.puzzle.model.Ranking
import java.util.*
import java.util.concurrent.TimeUnit

class RankingListAdapter : ListAdapter<Ranking, RankingListAdapter.ViewHolder>(
    ItemCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = RankingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val viewBinding: RankingBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: Ranking) {
            with(viewBinding) {
                rank.text = "${item.rank}"
                nickname.text = "${item.nickname}"
                size.text = item.size.toText(context)
                playTime.text = item.playTime.format()
            }
        }

        private fun Int.toText(context: Context): String =
            "$this-${context.string(R.string.puzzle)}"

        private fun Long.format(): String {
            val hours = TimeUnit.MILLISECONDS.toHours(this)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(this).minus(TimeUnit.HOURS.toMinutes(hours))
            val seconds = TimeUnit.MILLISECONDS.toSeconds(this).minus(TimeUnit.MINUTES.toSeconds(minutes))
            val milliseconds = rem(ONE_THOUSAND).div(TEN)

            return String.format(
                Locale.getDefault(),
                "%02d:%02d:%02d.%02d",
                hours,
                minutes,
                seconds,
                milliseconds
            )
        }
    }

    private class ItemCallback : DiffUtil.ItemCallback<Ranking>() {
        override fun areItemsTheSame(oldItem: Ranking, newItem: Ranking): Boolean {
            return oldItem.uid `is` newItem.uid
        }

        override fun areContentsTheSame(oldItem: Ranking, newItem: Ranking): Boolean {
            return oldItem `is` newItem
        }
    }
}
