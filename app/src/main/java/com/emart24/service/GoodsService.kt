package com.emart24.service

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import com.emart24.model.UnTakenGoods
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import java.security.MessageDigest
import java.util.*

class GoodsService {
    private val db: FirebaseFirestore = Firebase.firestore

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