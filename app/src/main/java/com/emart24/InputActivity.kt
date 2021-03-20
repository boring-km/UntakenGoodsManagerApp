package com.emart24

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emart24.component.DaggerGoodsComponent
import com.emart24.model.UnTakenGoods
import com.emart24.service.CommonService
import com.emart24.service.GoodsService
import com.emart24.service.GoodsModule
import com.emart24.service.QRService
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class InputActivity : AppCompatActivity() {

    @Inject lateinit var goodsService: GoodsService
    @Inject lateinit var qrService: QRService
    @Inject lateinit var commonService: CommonService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initializeGoodsModuleService()

        registerButton.setOnClickListener {

            val productName: String = productNameEditText.text.toString()
            val last4DigitsOfPhoneNumber: String = phoneEditText.text.toString()
            val nowDateTime = commonService.getNowDateTime()
            val qrData = qrService.generateQRCode(last4DigitsOfPhoneNumber, nowDateTime)
            val unTakenGoods = UnTakenGoods(productName, last4DigitsOfPhoneNumber, qrData, nowDateTime, false)   // 서버로 전송

            goodsService.registerGoods(unTakenGoods, {
                val moveToCreateActivity = Intent(this@InputActivity, CreateQRActivity::class.java)
                moveToCreateActivity.putExtra("qrcode", qrData)
                startActivity(moveToCreateActivity)
                finish()
            }, {
                Toast.makeText(applicationContext, "등록 실패", Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun initializeGoodsModuleService() {
        val component = DaggerGoodsComponent.builder()
            .goodsModule(GoodsModule())
            .build()
        component.inject(this)
    }
}