package com.emart24

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emart24.model.UnTakenGoods
import com.emart24.service.QRCode
import com.emart24.service.RetroService
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class InputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerButton.setOnClickListener {
            val moveToCreateActivity = Intent(this@InputActivity, CreateQRActivity::class.java)

            val name: String = productNameEditText.text.toString()
            val phone: String = phoneEditText.text.toString()
            val qrCode = QRCode()   // 추후에 Dagger 사용해서 의존성 주입하기

            val dateTimeFormat = SimpleDateFormat("YYYY:MM:DD hh:mm:ss")
            val nowDateTime = dateTimeFormat.format(Date(System.currentTimeMillis()))
            val qrData = qrCode.generateQRCode(name, nowDateTime)

            val unTakenGoods = UnTakenGoods(name, phone, qrData, nowDateTime)   // 서버로 전송

            val retrofit = Retrofit.Builder()
                .baseUrl("서버주소:8080/register")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(RetroService::class.java)

            val call: Call<Boolean> = service.registerGoods(unTakenGoods)
            call.enqueue(object: Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d("등록 결과", "onResponse: 성공, 결과: $result")
                        /*
                        DB에 저장해야 할 정보
                        1. 상품명
                        2. 휴대폰번호 뒷자리
                        3. QR 코드
                        4. 날짜 및 시간
                        */
                        if (result == true) {
                            // DB 저장 후 결과를 putExtra 하자
                            moveToCreateActivity.putExtra("qrcode", qrData)
                            startActivity(moveToCreateActivity)
                        } else {
                            Toast.makeText(applicationContext, "등록 실패", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.d("등록 결과", "onResponse: 실패")
                        Toast.makeText(applicationContext, "등록 실패", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("등록 결과", "onFailure: 실패, ${t.message}")
                    Toast.makeText(applicationContext, "등록 실패", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}