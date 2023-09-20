package com.example.kakaosearch.extension

import android.widget.ImageView
import com.example.kakaosearch.R

fun ImageView.loadHeartImage(isHeart: Boolean) {
    setImageResource(if(isHeart) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
}//viewExtention