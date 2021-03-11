package com.emart24

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emart24.component.DaggerGoodsComponent
import com.emart24.model.UnTakenGoods
import com.emart24.service.GoodsService
import com.emart24.service.GoodsModule
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class InputActivity : AppCompatActivity() {

    @Inject
    lateinit var goodsService: GoodsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initializeDBService()

        registerButton.setOnClickListener {
            val moveToCreateActivity = Intent(this@InputActivity, CreateQRActivity::class.java)

            val productName: String = productNameEditText.text.toString()
            val last4DigitsOfPhoneNumber: String = phoneEditText.text.toString()
            val nowDateTime = getNowDateTime()
            val qrData = goodsService.generateQRCode(last4DigitsOfPhoneNumber, nowDateTime)

            // 객체 생성? 그냥 데이터 클래스이기 때문에 굳이 팩토리 메서드로 만들 필요가 있을까
            val unTakenGoods = UnTakenGoods(productName, last4DigitsOfPhoneNumber, qrData, nowDateTime, false)   // 서버로 전송

            goodsService.registerGoods(unTakenGoods, {
                // DB 저장 후 결과를 putExtra 하자
                moveToCreateActivity.putExtra("qrcode", qrData)
                startActivity(moveToCreateActivity)
            }, {
                Toast.makeText(applicationContext, "등록 실패", Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun getNowDateTime(): String {
        val dateTimeFormat = SimpleDateFormat("YYYY:MM:DD hh:mm:ss")
        return dateTimeFormat.format(Date(System.currentTimeMillis()))
    }

    private fun initializeDBService() {
        val component = DaggerGoodsComponent.builder()
            .goodsModule(GoodsModule())
            .build()
        component.inject(this)
    }
}