package com.github.nisrulz.lanternproject

import android.content.Context
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor

fun ImageButton.setDrawable(@DrawableRes id: Int, context: Context) {
    this.setImageDrawable(ContextCompat.getDrawable(context, id))
}

fun LinearLayout.setBgColor(@ColorRes id: Int, context: Context) {
    this.setBackgroundColor(getColor(context, id))
}