package com.balocco.movies.common.usecase

import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import javax.inject.Inject

class ScreenSizeUseCase @Inject constructor(
        private val context: Context
) {

    fun screenWidth(): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay

        val size = Point()
        display.getSize(size)
        return size.x
    }

}