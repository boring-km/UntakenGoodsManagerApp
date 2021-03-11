package com.emart24.service

import android.annotation.SuppressLint
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

class QRCode {

    @SuppressLint("SimpleDateFormat")
    fun generateQRCode(testProductName: String, dateTime: String): String {
        val sha256 = MessageDigest.getInstance("SHA-256")
        val qrCodeByteArray = "$testProductName$dateTime".toByteArray()
        sha256.update(qrCodeByteArray)
        val qrCode = Arrays.toString(sha256.digest())
        println("QR Code: $qrCode, QR Code Size: ${qrCode.length}")
        return qrCode
    }
}
