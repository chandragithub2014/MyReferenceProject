package com.ref.bindingfeature.databindingrecyclerview.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String) {
/*    if (url != "") {
        // Picasso.with(imageView.getContext()).load(url).resize(200, 200).into(imageView).
       // Picasso.get().load(url).into(this)
    }*/
    Glide.with(this.context)
        .load(url).apply(RequestOptions().circleCrop())
        .override(100,100)
        .into(this)
}