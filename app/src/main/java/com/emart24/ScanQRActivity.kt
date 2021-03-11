package com.emart24

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emart24.component.DaggerGoodsComponent
import com.emart24.service.GoodsModule
import com.emart24.service.GoodsService
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_scan_qr.*
import javax.inject.Inject


class ScanQRActivity : AppCompatActivity() {

    private var qrScan: IntentIntegrator? = null

    @Inject
    lateinit var goodsService: GoodsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr)
        initializeDBService()
        scanning()

        rescan_Button.setOnClickListener {
            scanning()
        }
        back_Button.setOnClickListener {
            finish()
        }
    }

    private fun scanning() {
        qrScan = IntentIntegrator(this)
        qrScan!!.setOrientationLocked(false)
            .setBarcodeImageEnabled(true)
            .setPrompt("QR 코드 스캔")
            .initiateScan()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "인식못함", Toast.LENGTH_SHORT).show()
                return
            }
            val qrCode = result.contents
            goodsService.findGoods(
                qrCode, { documentSnapshot ->
                    val intent = Intent(this@ScanQRActivity, GoodsResultActivity::class.java)
                    val dataMap: Map<String, Any>? = documentSnapshot.data

                    val isAccepted = dataMap!!.getOrDefault("accept", false)
                    if (isAccepted != true) {
                        intent.putExtra("productName", dataMap.getOrDefault("name", "").toString())
                        intent.putExtra("dateTime", dataMap.getOrDefault("dateTime", "").toString())
                        intent.putExtra("qrcode", dataMap.getOrDefault("qrcode", "").toString())
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "해당 상품 이미 수령함", Toast.LENGTH_SHORT).show()
                    }
                }, { error ->
                    Toast.makeText(this, "해당 상품 없음", Toast.LENGTH_SHORT).show()
                    Log.e("상품 스캔 오류", error.message.toString())
                }
            )
        }
    }

    private fun initializeDBService() {
        val component = DaggerGoodsComponent.builder()
            .goodsModule(GoodsModule())
            .build()
        component.inject(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.fade_in_anim, R.anim.fade_out_anim)
    }
}
