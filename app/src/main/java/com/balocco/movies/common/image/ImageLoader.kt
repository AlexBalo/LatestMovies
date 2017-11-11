package com.balocco.movies.common.image

import android.widget.ImageView
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ImageLoader @Inject constructor(
        private val picasso: Picasso
) {

    fun loadImageInView(url: String,
                        imageView: ImageView) {
        picasso.load(url).into(imageView)
    }

}