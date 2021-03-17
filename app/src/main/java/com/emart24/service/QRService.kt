package com.emart24.service

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.security.MessageDigest
import java.util.*

class QRService {

    @SuppressLint("SimpleDateFormat")
    fun generateQRCode(last4DigitsOfPhoneNumber: String, dateTime: String): String {
        val sha256 = MessageDigest.getInstance("SHA-256")
        val qrCodeByteArray = "$last4DigitsOfPhoneNumber$dateTime".toByteArray()
        sha256.update(qrCodeByteArray)
        val qrCode = Arrays.toString(sha256.digest())
        println("QR Code: $qrCode, QR Code Size: ${qrCode.length}")
        return qrCode
    }

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
