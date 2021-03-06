package com.emart24

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emart24.bitmap.BitmapImage
import com.emart24.bitmap.NoneQRCodeImage
import com.emart24.bitmap.QRCodeImage
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_create_qr.*


open class CreateQRActivity : AppCompatActivity() {

    private var qrImage: BitmapImage = NoneQRCodeImage()
    private lateinit var bitmap: Bitmap

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_qr)

        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val qrcodeData = intent.getStringExtra("qrcode")

        Toast.makeText(this, "이름은 $name 전화번호 뒷자리는 $phone", Toast.LENGTH_LONG).show()
        createQRCodeImage(qrcodeData!!)
    }

    private fun createQRCodeImage(randomQRData: String) {
        val writer = QRCodeWriter()
        try {
            val matrix = writer.encode(randomQRData, BarcodeFormat.QR_CODE, 300, 300)
            val encoder = BarcodeEncoder()

            bitmap = encoder.createBitmap(matrix)
            qrImage = QRCodeImage()

            qrcode_ImageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.fade_in_anim, R.anim.fade_out_anim)
    }
}
