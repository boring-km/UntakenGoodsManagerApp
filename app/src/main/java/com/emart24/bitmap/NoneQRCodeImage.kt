package com.emart24.bitmap

import android.content.ContentResolver
import android.graphics.Bitmap
import com.emart24.bitmap.BitmapImage

class NoneQRCodeImage : BitmapImage() {
    override fun saveImage(
        image: Bitmap,
        contentResolver: ContentResolver
    ): Boolean {
        return false
    }

}