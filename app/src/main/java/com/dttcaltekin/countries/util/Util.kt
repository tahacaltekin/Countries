package com.dttcaltekin.countries.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dttcaltekin.countries.R

fun ImageView.downloadFromUrl(url : String?, progressDrawable: CircularProgressDrawable) {

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_launcher_background)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

fun placeHolderProgressBar(context : Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}