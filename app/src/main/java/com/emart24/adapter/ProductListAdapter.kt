package com.emart24.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.emart24.R
import com.emart24.model.UnTakenGoods

class ProductListAdapter: BaseAdapter(), Filterable {

    private val list = ArrayList<UnTakenGoods>()
    private var filteredList: ArrayList<UnTakenGoods> = list
    private var listFilter: Filter? = null

    override fun getCount(): Int {
        return filteredList.size
    }

    override fun getItem(position: Int): UnTakenGoods {
        return filteredList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        val context = viewGroup.context
        var convertView: View? = view
        if (convertView == null) {
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.product_list_item, viewGroup, false)
        }
        val dateView = convertView!!.findViewById<TextView>(R.id.DateTextView)
        val nameView = convertView.findViewById<TextView>(R.id.ProductNameTextView)
        val acceptView = convertView.findViewById<TextView>(R.id.IsAcceptTextView)

        val item = filteredList[position]
        dateView.text = item.dateTime.substring(2, 13)
        nameView.text = item.name
        if (item.accept) {
            acceptView.text = "수령"
        } else {
            acceptView.text = "미수령"
        }
        return convertView
    }

    fun addItems(items: ArrayList<UnTakenGoods>) {
        filteredList.addAll(items)
    }

    fun addItem(item: UnTakenGoods) {
        filteredList.add(item)
    }

    fun clear() {
        filteredList.clear()
    }

    override fun getFilter(): Filter? {
        if (listFilter == null) {
            listFilter = ListFilter()
        }
        return listFilter
    }

    inner class ListFilter : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val results = FilterResults()
            if (constraint.isEmpty()) {
                results.values = list
                results.count = list.size
            } else {
                val itemList: ArrayList<UnTakenGoods> = ArrayList()
                for (item in list) {
                    if (item.phone.contains(constraint.toString())) {
                        itemList.add(item)
                    }
                }
                results.values = itemList
                results.count = itemList.size
            }
            return results
        }

        override fun publishResults(charSequence: CharSequence, results: FilterResults) {
            @Suppress("UNCHECKED_CAST")
            filteredList = results.values as ArrayList<UnTakenGoods>
            if (results.count > 0) {
                notifyDataSetChanged()
            } else {
                notifyDataSetInvalidated()
            }
        }
    }
}