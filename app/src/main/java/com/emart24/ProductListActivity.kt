package com.emart24

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.emart24.adapter.ProductListAdapter
import com.emart24.model.UnTakenGoods
import kotlinx.android.synthetic.main.activity_list.*
import java.util.*

class ProductListActivity: AppCompatActivity() {

    private lateinit var adapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // TODO Firebase에서 데이터 가져오기
        val resultList = Array<UnTakenGoods>(5) { i ->
            UnTakenGoods("테스트 $i", "1234", "qr", "21년 3월 14일", false)
        }
        resultList[0] = UnTakenGoods("테스트 0", "1234", "qr", "21년 3월 14일", false)
        resultList[1] = UnTakenGoods("테스트 1", "1111", "qr", "21년 3월 14일", false)
        resultList[2] = UnTakenGoods("테스트 2", "2222", "qr", "21년 3월 14일", false)
        resultList[3] = UnTakenGoods("테스트 3", "8887", "qr", "21년 3월 14일", false)

        adapter = ProductListAdapter()
        ProductListView.adapter = adapter

        adapter.addItems(resultList)
        adapter.notifyDataSetChanged()

        editPhoneNumberView.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val filterText = s.toString()
                val length = filterText.length
                adapter.clear()
                Arrays.stream(resultList).filter { item -> item.phone.substring(0, length) == filterText }.forEach { item ->
                    adapter.addItem(item)
                }
                adapter.notifyDataSetChanged()
            }

        })
    }
}