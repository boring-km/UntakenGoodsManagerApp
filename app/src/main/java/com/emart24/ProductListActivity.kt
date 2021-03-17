package com.emart24

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emart24.adapter.ProductListAdapter
import com.emart24.component.DaggerGoodsComponent
import com.emart24.model.UnTakenGoods
import com.emart24.service.GoodsModule
import com.emart24.service.GoodsService
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_list.*
import java.util.*
import javax.inject.Inject
import android.os.HandlerThread


class ProductListActivity : AppCompatActivity() {

    private lateinit var adapter: ProductListAdapter
    @Inject lateinit var goodsService: GoodsService
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        initializeGoodsModuleService()

        val resultList = ArrayList<UnTakenGoods>()

        setHandler()
        handler.post {
            goodsService.findAllGoods({ qs: QuerySnapshot ->
                qs.documents.forEach { ds ->
                    val goods = ds.toObject(UnTakenGoods::class.java) as UnTakenGoods
                    resultList.add(goods)
                }
                adapter.addItems(resultList)
                adapter.notifyDataSetChanged()
            }, {
                Toast.makeText(applicationContext, "데이터 없음", Toast.LENGTH_SHORT).show()
            })
        }

        setListViewAdapter()

        editPhoneNumberView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val filterText = s.toString()
                val length = filterText.length
                adapter.clear()
                resultList.filter { item -> item.phone.substring(0, length) == filterText }
                    .forEach { item ->
                        adapter.addItem(item)
                    }
                adapter.notifyDataSetChanged()
            }

        })
    }

    private fun setHandler() {
        val handlerThread = HandlerThread("android_handler")
        handlerThread.start()
        handler = Handler(handlerThread.looper)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.looper.quit()
    }

    private fun setListViewAdapter() {
        adapter = ProductListAdapter()
        ProductListView.adapter = adapter
        ProductListView.setOnItemClickListener { parent, view, position, id ->
            val index = adapter.getItemId(position).toInt()
            val product: UnTakenGoods = adapter.getItem(index)
            val intent = Intent(this@ProductListActivity, ProductDetailActivity::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        }
    }

    private fun initializeGoodsModuleService() {
        val component = DaggerGoodsComponent.builder()
            .goodsModule(GoodsModule())
            .build()
        component.inject(this)
    }
}