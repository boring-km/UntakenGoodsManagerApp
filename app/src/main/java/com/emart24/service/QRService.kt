package com.emart24.service

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

class QRService {
    fun createQR(qrcodeData: String?): Bitmap? {
        val writer = QRCodeWriter()
        val matrix = writer.encode(qrcodeData, BarcodeFormat.QR_CODE, 300, 300)
        val encoder = BarcodeEncoder()
        return encoder.createBitmap(matrix)
    }
}
