package com.emart24

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_scan_qr.*

class ScanQRActivity : AppCompatActivity() {

    private var qrScan: IntentIntegrator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "인식못함", Toast.LENGTH_SHORT).show()
                return
            }
            Toast.makeText(this, result.contents, Toast.LENGTH_LONG).show()
        } else {
            super.onActivityResult(requestCode, resultCode, data)   // TODO: 사용 이유 알아내기
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.fade_in_anim, R.anim.fade_out_anim)
    }
}
