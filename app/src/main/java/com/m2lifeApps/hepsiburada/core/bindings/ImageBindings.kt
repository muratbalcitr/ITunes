package com.m2lifeApps.hepsiburada.core

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:imageUrl")
fun AppCompatImageView.loadImage(url: Int?) {
    Glide.with(this).load(url).into(this)
}

@BindingAdapter("app:imageUrl")
fun AppCompatImageView.loadImage(bitmap: Bitmap?) {
    Glide.with(this).load(bitmap).into(this)
}

@BindingAdapter("app:imageUrl")
fun AppCompatImageView.loadImage(url: Drawable?) {
    Glide.with(this).load(url).into(this)
}

@BindingAdapter("app:imageUrl")
fun AppCompatImageView.loadImage(url: String?) {
    Glide.with(this).load(Uri.parse(url)).into(this)
}
