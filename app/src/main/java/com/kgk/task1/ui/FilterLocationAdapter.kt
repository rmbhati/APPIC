package com.kgk.task1.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kgk.task1.R
import com.kgk.task1.databinding.RowFilterBinding
import kotlinx.android.synthetic.main.row_filter.view.*
import kotlin.collections.ArrayList

class FilterLocationAdapter(
    private val clickListener: ClickListener, private var dataList: ArrayList<*>,
    private val isFrom: String,
) : RecyclerView.Adapter<FilterLocationAdapter.ViewHolder>() {

    interface ClickListener {
        fun onChecked(isFrom: String, pos: Int, check: Boolean)
    }

    class ViewHolder(itemView: RowFilterBinding) : RecyclerView.ViewHolder(itemView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.row_filter, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (isFrom) {
            "Account" -> {
                val data: hierarchyData = dataList[position] as hierarchyData
                holder.itemView.checkbox.text = data.accountNumber
                holder.itemView.checkbox.isChecked = data.checked
                holder.itemView.checkbox.setOnCheckedChangeListener { _, isChecked ->
                    clickListener.onChecked(isFrom, position, isChecked)
                }
            }
            "Brand" -> {
                val data: brandNameListData = dataList[position] as brandNameListData
                holder.itemView.checkbox.text = data.brandName
                holder.itemView.checkbox.isChecked = data.checked
                holder.itemView.checkbox.setOnCheckedChangeListener { _, isChecked ->
                    clickListener.onChecked(isFrom, position, isChecked)
                }
            }
            "Location" -> {
                val data: locationNameListData = dataList[position] as locationNameListData
                holder.itemView.checkbox.text = data.locationName
                holder.itemView.checkbox.isChecked = data.checked
                holder.itemView.checkbox.setOnCheckedChangeListener { _, isChecked ->
                    clickListener.onChecked(isFrom, position, isChecked)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateList(list: List<*>) {
        dataList = list as ArrayList<*>
        notifyDataSetChanged()
    }

}