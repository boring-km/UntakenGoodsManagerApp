package com.emart24

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emart24.component.DaggerGoodsComponent
import com.emart24.service.GoodsModule
import com.emart24.service.GoodsService
import kotlinx.android.synthetic.main.activity_accept.*
import javax.inject.Inject

class GoodsResultActivity: AppCompatActivity() {

    @Inject
    lateinit var goodsService: GoodsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accept)
        initializeGoodsModuleService()

        val name = intent.getStringExtra("productName")
        val dateTime = intent.getStringExtra("dateTime")
        val qrcode = intent.getStringExtra("qrcode")

        if (name.isNullOrEmpty() || dateTime.isNullOrEmpty()) {
            Toast.makeText(this, "실패! $name, $dateTime", Toast.LENGTH_SHORT).show()
        } else {
            nameResultTextView.text = "상품명: $name"
            dateTimeResultTextView.text = "구매일시\n$dateTime"
            Toast.makeText(this, "성공! $name, $dateTime", Toast.LENGTH_SHORT).show()
        }

        acceptButton.setOnClickListener {
            if (!qrcode.isNullOrEmpty()) {
                goodsService.acceptGoods(qrcode,
                    {
                        Toast.makeText(applicationContext, "상품 수령 완료", Toast.LENGTH_SHORT).show()
                        acceptButton.text = "상품 수령 완료됨"
                    }, {
                        Toast.makeText(applicationContext, "상품 수령 실패", Toast.LENGTH_SHORT).show()
                        acceptButton.text = "상품 수령 실패(재시도)"
                    })
            }
        }

        rescanButton.setOnClickListener {
            finish()
            startActivity(Intent(this@GoodsResultActivity, ScanQRActivity::class.java))
        }
    }

    private fun initializeGoodsModuleService() {
        val component = DaggerGoodsComponent.builder()
            .goodsModule(GoodsModule())
            .build()
        component.inject(this)
    }
}