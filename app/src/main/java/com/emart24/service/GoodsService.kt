package com.emart24.service

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.emart24.model.UnTakenGoods
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.security.MessageDigest
import java.util.*

class GoodsService {
    private val db: FirebaseFirestore = Firebase.firestore

    fun registerGoods(goods: UnTakenGoods, onSuccessListener: OnSuccessListener<Void>, onFailureListener: OnFailureListener) {
        db.collection("goods").document(goods.qrcode).set(goods)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun findAllGoods(onSuccessListener: OnSuccessListener<QuerySnapshot>, onFailureListener: OnFailureListener) {
        db.collection("goods")
            .get()
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }

    fun findGoodsByQR(qrCode: String, onSuccessListener: OnSuccessListener<DocumentSnapshot>, onFailureListener: OnFailureListener) {
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

    fun changeAccept(qrcode: String, accept: Boolean, onSuccessListener: OnSuccessListener<Void>, onFailureListener: OnFailureListener) {
        db.collection("goods")
            .document(qrcode)
            .update("accept", !accept)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }

    fun deleteItem(goods: UnTakenGoods, onSuccessListener: OnSuccessListener<Void>, onFailureListener: OnFailureListener) {
        db.collection("trash").document(goods.qrcode).set(goods)
            .addOnSuccessListener {
                db.collection("goods")
                    .document(goods.qrcode)
                    .delete()
                    .addOnSuccessListener(onSuccessListener)
                    .addOnFailureListener(onFailureListener)
            }
            .addOnFailureListener(onFailureListener)


    }

}