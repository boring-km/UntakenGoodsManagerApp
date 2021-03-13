package com.emart24.service

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

class QRService {
    fun createQR(qrcodeData: String?): Bitmap? {
        val writer = QRCodeWriter()
        val matrix = writer.encode(qrcodeData, BarcodeFormat.QR_CODE, 300, 300)
        val encoder = BarcodeEncoder()
        return encoder.createBitmap(matrix)
    }

    fun scanning(activity: Activity) {
        val qrScan = IntentIntegrator(activity)
        qrScan.setOrientationLocked(false)
            .setBarcodeImageEnabled(true)
            .setPrompt("QR 코드 스캔")
            .initiateScan()
    }

    fun parseActivityResult(requestCode: Int, resultCode: Int, data: Intent?): IntentResult {
        return IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
    }
}
