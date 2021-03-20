package com.emart24.service

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class CommonService {
    @SuppressLint("SimpleDateFormat")
    fun getNowDateTime(): String {
        val dateTimeFormat = SimpleDateFormat("YYYY년 MM월 dd일 aa hh시 mm분")
        return dateTimeFormat.format(Date(System.currentTimeMillis()))
    }
}