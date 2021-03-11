package com.emart24

import com.emart24.service.GoodsService
import org.junit.Assert.assertNotNull
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class RegisterTest {

    @Test
    internal fun 등록을_위한_암호화_테스트() {
        val testProductName = "0644"
        val dateTimeFormat = SimpleDateFormat("YYYY:MM:DD hh:mm:ss")
        val dateTime = dateTimeFormat.format(Date(System.currentTimeMillis()))
        val goodsService = GoodsService()
        val qrCode = goodsService.generateQRCode(testProductName, dateTime)
        assertNotNull(qrCode)
    }
}