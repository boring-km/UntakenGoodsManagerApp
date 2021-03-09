package com.emart24

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InputActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerButton.setOnClickListener {
            val moveToCreateActivity = Intent(this@InputActivity, CreateQRActivity::class.java)

            val name: String = productNameEditText.text.toString()
            val phone: String = phoneEditText.text.toString()
            moveToCreateActivity.putExtra("name", name)
            moveToCreateActivity.putExtra("phone", phone)
//            moveToCreateActivity.putExtra("qrcode", )

            startActivity(moveToCreateActivity)
        }
    }
}