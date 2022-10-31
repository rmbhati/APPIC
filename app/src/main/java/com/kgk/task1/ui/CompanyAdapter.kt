package com.kgk.task1.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kgk.task1.R
import com.kgk.task1.databinding.RowCompanyBinding
import com.kgk.task1.utils.toast
import kotlinx.android.synthetic.main.row_company.view.*
import kotlin.math.abs

class CompanyAdapter(
    private var context: Context, private var dataList: List<FilterData>,
) : RecyclerView.Adapter<CompanyAdapter.ViewHolder>() {

    class ViewHolder(itemView: RowCompanyBinding) : RecyclerView.ViewHolder(itemView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.row_company, parent, false
            )
        )
    }

    override fun onBindViewHolder(holderScheduled: ViewHolder, position: Int) {
        holderScheduled.itemView.text.text = dataList[position].companyName
        holderScheduled.itemView.setOnClickListener {
            context.toast(dataList[position].companyName)
            //we can change selected bg color and selection of unselected data using one kery isSelected = true and false.
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


}