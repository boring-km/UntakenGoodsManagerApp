package com.emart24.service

import org.junit.Test


class CommonServiceTest {
    @Test
    fun testGetDateTime() {
        val commonService = CommonService()
        println("íėŽėę°: ${commonService.getNowDateTime()}")
    }
}