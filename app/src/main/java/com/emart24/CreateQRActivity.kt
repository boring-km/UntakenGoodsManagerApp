package com.emart24

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emart24.component.DaggerGoodsComponent
import com.emart24.service.GoodsModule
import com.emart24.service.QRService
import kotlinx.android.synthetic.main.activity_create_qr.*
import javax.inject.Inject


class CreateQRActivity : AppCompatActivity() {

    @Inject
    private lateinit var qrService: QRService

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_qr)
        initializeDBService()

        val qrcodeData = intent.getStringExtra("qrcode")
        Toast.makeText(this, qrcodeData, Toast.LENGTH_LONG).show()
        val bitmap = qrService.createQR(qrcodeData)
        qrcode_ImageView.setImageBitmap(bitmap)
    }

    private fun initializeDBService() {
        val component = DaggerGoodsComponent.builder()
            .goodsModule(GoodsModule())
            .build()
        component.inject(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in_anim, R.anim.fade_out_anim)
    }
}
