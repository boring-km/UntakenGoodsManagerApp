package com.emart24

import com.emart24.service.QRCode
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class RegisterTest {

    @Test
    internal fun 등록을_위한_암호화_테스트() {
        val testProductName = "바나나우유"
        val qrCodeData = QRCode()
        val dateTimeFormat = SimpleDateFormat("YYYY:MM:DD hh:mm:ss")
        val dateTime = dateTimeFormat.format(Date(System.currentTimeMillis()))
        val qrCode = qrCodeData.generateQRCode(testProductName, dateTime)
        assertNotNull(qrCode)
    }
}