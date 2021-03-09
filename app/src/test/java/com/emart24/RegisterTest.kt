package com.emart24

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class RegisterTest {

    @Test
    internal fun 등록을_위한_암호화_테스트() {
        val testProductName = "바나나우유"
        val dateTime: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY:MM:DD hh:mm:ss"))
        val sha256 = MessageDigest.getInstance("SHA-256")
        val qrCodeByteArray = "$testProductName$dateTime".toByteArray()
        sha256.update(qrCodeByteArray)
        val qrCode = Arrays.toString(sha256.digest())
        println("QR Code: $qrCode, QR Code Size: ${qrCode.length}")
        assertNotNull(qrCode)
    }
}