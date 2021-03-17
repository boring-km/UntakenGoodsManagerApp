package com.emart24

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emart24.component.DaggerGoodsComponent
import com.emart24.model.UnTakenGoods
import com.emart24.service.GoodsModule
import com.emart24.service.QRService
import kotlinx.android.synthetic.main.activity_create_qr.*
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.qrcode_ImageView
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
        dateTimeResultTextView.text = product.dateTime
        acceptTextView.text = if (product.accept) "수령" else "미수령"

        val qrcode = product.qrcode
        val bitmap = qrService.createQR(qrcode)
        qrcode_ImageView.setImageBitmap(bitmap)

        detailCloseButton.setOnClickListener {
            finish()
        }
    }

    private fun initializeGoodsModuleService() {
        val component = DaggerGoodsComponent.builder()
            .goodsModule(GoodsModule())
            .build()
        component.inject(this)
    }
}