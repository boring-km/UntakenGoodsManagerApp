package com.emart24.service

import android.annotation.SuppressLint
import com.emart24.model.UnTakenGoods
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.security.MessageDigest
import java.util.*

class GoodsService {
    private val db: FirebaseFirestore = Firebase.firestore
    /*
        DB에 저장해야 할 정보
        1. 상품명
        2. 휴대폰번호 뒷자리
        3. QR 코드
        4. 날짜 및 시간
    */
    fun registerGoods(goods: UnTakenGoods, onSuccessListener: OnSuccessListener<Void>, onFailureListener: OnFailureListener) {
        db.collection("goods").document(goods.qrcode).set(goods)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }

    fun findGoods(qrCode: String, onSuccessListener: OnSuccessListener<DocumentSnapshot>, onFailureListener: OnFailureListener) {
        db.collection("goods")
            .document(qrCode)
            .get()
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }

    fun acceptGoods(qrcode: String, onSuccessListener: OnSuccessListener<Void>, onFailureListener: OnFailureListener) {
        db.collection("goods")
            .document(qrcode)
            .update("accept", true)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }

    @SuppressLint("SimpleDateFormat")
    fun generateQRCode(last4DigitsOfPhoneNumber: String, dateTime: String): String {
        val sha256 = MessageDigest.getInstance("SHA-256")
        val qrCodeByteArray = "$last4DigitsOfPhoneNumber$dateTime".toByteArray()
        sha256.update(qrCodeByteArray)
        val qrCode = Arrays.toString(sha256.digest())
        println("QR Code: $qrCode, QR Code Size: ${qrCode.length}")
        return qrCode
    }
}