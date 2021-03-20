package com.emart24

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emart24.component.DaggerGoodsComponent
import com.emart24.model.UnTakenGoods
import com.emart24.service.GoodsModule
import com.emart24.service.QRService
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class ProductDetailActivity: AppCompatActivity() {

    @Inject lateinit var qrService: QRService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        initializeGoodsModuleService()

        val product: UnTakenGoods = intent.extras!!.getParcelable("product")!!
        nameResultTextView.text = product.name
        phoneResultTextView.text = product.phone

        val (yearMonthDay, time) = setDateTime(product)

        dateTimeResultTextView.text = yearMonthDay + time
        acceptTextView.text = if (product.accept) "수령" else "미수령"

        val qrcode = product.qrcode
        val bitmap = qrService.createQR(qrcode)
        qrcode_ImageView.setImageBitmap(bitmap)

        detailCloseButton.setOnClickListener {
            finish()
        }
    }

    private fun setDateTime(product: UnTakenGoods): Pair<String, String> {
        val dateTime = product.dateTime.split(" ")
        val yearMonthDay = dateTime[0] + " " + dateTime[1] + " " + dateTime[2] + "\n"
        val time = dateTime[3] + " " + dateTime[4] + " " + dateTime[5]
        return Pair(yearMonthDay, time)
    }

    private fun initializeGoodsModuleService() {
        val component = DaggerGoodsComponent.builder()
            .goodsModule(GoodsModule())
            .build()
        component.inject(this)
    }
}