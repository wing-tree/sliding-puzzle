package com.wing.tree.sid.sliding.puzzle.bindingAdapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.wing.tree.sid.sliding.puzzle.extension.setDongleText

@BindingAdapter("dongleText")
fun setDongleText(view: TextView, text: CharSequence) {
    view.setDongleText(text)
}
