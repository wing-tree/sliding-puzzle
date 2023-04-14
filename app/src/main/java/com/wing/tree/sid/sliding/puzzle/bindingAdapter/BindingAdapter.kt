package com.wing.tree.sid.sliding.puzzle.bindingAdapter

import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.wing.tree.sid.sliding.puzzle.extension.setDongleText

@BindingAdapter
fun setDongleText(view: TextView, text: String) {
    view.setDongleText(text)
}

@BindingAdapter
fun setDongleText(view: TextView, @StringRes resid: Int) {
    view.setDongleText(resid)
}
