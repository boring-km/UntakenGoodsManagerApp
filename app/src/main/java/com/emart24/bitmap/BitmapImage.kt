package com.emart24.bitmap

import android.content.ContentResolver
import android.graphics.Bitmap

abstract class BitmapImage {

    abstract fun saveImage(
        image: Bitmap,
        contentResolver: ContentResolver
    ): Boolean
}