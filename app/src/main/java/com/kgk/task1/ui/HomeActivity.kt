package com.kgk.task1.ui

import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.kgk.task1.R
import com.kgk.task1.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : BaseActivity(), FilterLocationAdapter.ClickListener {
    private lateinit var jsonData: ResponseData
    private lateinit var listAccount: ArrayList<hierarchyData>
    private lateinit var listBrands: ArrayList<brandNameListData>
    private lateinit var listLocation: ArrayList<locationNameListData>
    private var filterAdapter: FilterLocationAdapter? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var checkbox: CheckBox


    override fun initView() {
        setContentView(R.layout.activity_main)
        toolbar.title = ""
        setSupportActionBar(toolbar)
    }

    override fun initData() {
        val response =
            "{ \"status\": \"Success\", \"message\": \"Data Available\", \"errorCode\": \"L128\", \"filterData\": [ { \"Cif\": \"7016360\", \"companyName\": \"PHARMA ZONE GENERAL CO\", \"hierarchy\": [ { \"accountNumber\": \"023170163600014402001\", \"brandNameList\": [ { \"brandName\": \"DELTAMED KHAITAN PH\", \"locationNameList\": [ { \"locationName\": \"KHAITAN\", \"merchantNumber\": [ { \"mid\": \"354902077\", \"outletNumber\": [ \"12764173\" ] }, { \"mid\": \"354902078\", \"outletNumber\": [ \"12764174\" ] } ] } ] }, { \"brandName\": \"FARMA ZONE PHARMACY\", \"locationNameList\": [ { \"locationName\": \"HAWALLY\", \"merchantNumber\": [ { \"mid\": \"354902033\", \"outletNumber\": [ \"29910294\" ] }, { \"mid\": \"354902066\", \"outletNumber\": [ \"12764162\" ] }, { \"mid\": \"354902079\", \"outletNumber\": [ \"28800053\" ] } ] }, { \"locationName\": \"SALMIYA\", \"merchantNumber\": [ { \"mid\": \"354902120\", \"outletNumber\": [ \"29905829\" ] }, { \"mid\": \"354902098\", \"outletNumber\": [ \"44000645\" ] } ] }, { \"locationName\": \"MIDAN HAWALLY\", \"merchantNumber\": [ { \"mid\": \"354902149\", \"outletNumber\": [ \"29916071\" ] } ] }, { \"locationName\": \"ALJAHRA\", \"merchantNumber\": [ { \"mid\": \"354902011\", \"outletNumber\": [ \"29911084\" ] } ] }, { \"locationName\": \"JABRIYA\", \"merchantNumber\": [ { \"mid\": \"354902132\", \"outletNumber\": [ \"29911092\" ] }, { \"mid\": \"354902133\", \"outletNumber\": [ \"29911093\" ] }, { \"mid\": \"354902128\", \"outletNumber\": [ \"29907443\" ] }, { \"mid\": \"354902127\", \"outletNumber\": [ \"29907442\" ] } ] }, { \"locationName\": \"ALSALAM\", \"merchantNumber\": [ { \"mid\": \"354902016\", \"outletNumber\": [ \"29800218\" ] }, { \"mid\": \"354902017\", \"outletNumber\": [ \"29800227\" ] } ] }, { \"locationName\": \"HATEEN\", \"merchantNumber\": [ { \"mid\": \"354902043\", \"outletNumber\": [ \"29804811\" ] }, { \"mid\": \"354902018\", \"outletNumber\": [ \"29800220\" ] } ] } ] }, { \"brandName\": \"TAD KHAITAN PH\", \"locationNameList\": [ { \"locationName\": \"KHAI̥TAN\", \"merchantNumber\": [ { \"mid\": \"354902076\", \"outletNumber\": [ \"12764172\" ] }, { \"mid\": \"354902075\", \"outletNumber\": [ \"12764171\" ] } ] } ] }, { \"brandName\": \"SHOHADA PH\", \"locationNameList\": [ { \"locationName\": \"KHAITAN\", \"merchantNumber\": [ { \"mid\": \"354902074\", \"outletNumber\": [ \"12764170\" ] } ] } ] } ] }, { \"accountNumber\": \"023170163600014402002\", \"brandNameList\": [ { \"brandName\": \"DELTAMED KHAITAN PH\", \"locationNameList\": [ { \"locationName\": \"HATEEN\", \"merchantNumber\": [ { \"mid\": \"3549020778\", \"outletNumber\": [ \"127641738\" ] }, { \"mid\": \"3549020789\", \"outletNumber\": [ \"127641749\" ] } ] } ] }, { \"brandName\": \"TAD KHAITAN PH\", \"locationNameList\": [ { \"locationName\": \"MIDAN HAWALLY\", \"merchantNumber\": [ { \"mid\": \"3549020761\", \"outletNumber\": [ \"127641721\" ] }, { \"mid\": \"3549020752\", \"outletNumber\": [ \"127641712\" ] } ] } ] }, { \"brandName\": \"SHOHADA PH\", \"locationNameList\": [ { \"locationName\": \"ALSALAM\", \"merchantNumber\": [ { \"mid\": \"3549020743\", \"outletNumber\": [ \"127641703\" ] } ] } ] } ] } ] } ] }"
        jsonData = Gson().fromJson(response, ResponseData::class.java)

        listAccount = ArrayList()
        listBrands = ArrayList()
        listLocation = ArrayList()
        for (filter in jsonData.filterData) {
            for (hierar in filter.hierarchy) {
                listAccount.add(hierar)
                for (brand in hierar.brandNameList) {
                    listBrands.add(brand)
                    for (location in brand.locationNameList) {
                        listLocation.add(location)
                    }
                }
            }
        }

        header.text = jsonData.filterData[0].companyName

        openBottomSheet()
    }

    private fun openBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
        val close = view.findViewById<ImageView>(R.id.close)
        val rvCompany = view.findViewById<RecyclerView>(R.id.rvCompany)
        val selAccount = view.findViewById<TextView>(R.id.selAccount)
        val selBrand = view.findViewById<TextView>(R.id.selBrand)
        val selLocation = view.findViewById<TextView>(R.id.selLocation)
        val accountCount = view.findViewById<TextView>(R.id.accountCount)
        val brandCount = view.findViewById<TextView>(R.id.brandCount)
        val locCount = view.findViewById<TextView>(R.id.locCount)
        val llAccount = view.findViewById<LinearLayout>(R.id.llAccount)
        val llBrand = view.findViewById<LinearLayout>(R.id.llBrand)
        val llLocation = view.findViewById<LinearLayout>(R.id.llLocation)

        rvCompany.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
            false)
        rvCompany.adapter = CompanyAdapter(this, jsonData.filterData)

        var countAccount = 0
        var countBrand = 0
        var countLocation = 0
        for (n in listAccount) if (n.checked) countAccount++
        for (n in listBrands) if (n.checked) countBrand++
        for (n in listLocation) if (n.checked) countLocation++
        selAccount.text = "Acc No : $countAccount"
        selBrand.text = "Brand : $countBrand"
        selLocation.text = "Location : $countLocation"
        accountCount.text = "$countAccount"
        brandCount.text = "$countBrand"
        locCount.text = "$countLocation"

        close.setOnClickListener {
            dialog.dismiss()
        }
        llAccount.setOnClickListener {
            openFilter("Account")
            dialog.dismiss()
        }
        llBrand.setOnClickListener {
            openFilter("Brand")
            dialog.dismiss()
        }
        llLocation.setOnClickListener {
            openFilter("Location")
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)

        dialog.show()
    }

    private fun openFilter(isFrom: String) {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_filter, null)

        val close = view.findViewById<ImageView>(R.id.close)
        val etSearch = view.findViewById<EditText>(R.id.etSearch)
        checkbox = view.findViewById(R.id.checkbox)
        val clear = view.findViewById<TextView>(R.id.clear)
        recyclerView = view.findViewById(R.id.recyclerView)

        etSearch.hint = "Search for $isFrom"
        recyclerView.layoutManager = LinearLayoutManager(this)
        when (isFrom) {
            "Account" -> {
                filterAdapter = FilterLocationAdapter(this, listAccount, isFrom)
            }
            "Brand" -> {
                filterAdapter = FilterLocationAdapter(this, listBrands, isFrom)
            }
            "Location" -> {
                filterAdapter = FilterLocationAdapter(this, listLocation, isFrom)
            }
        }
        recyclerView.adapter = filterAdapter

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filter(s.toString(), isFrom)
            }
        })
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                allCheckBox(true, isFrom)
            } else {
                allCheckBox(false, isFrom)
            }
            setAdapterData(isFrom)
        }
        clear.setOnClickListener {
            checkbox.isChecked = false
        }
        close.setOnClickListener {
            dialog.dismiss()
            openBottomSheet()
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)

        dialog.show()
    }

    private fun allCheckBox(sts: Boolean, isFrom: String) {
        when (isFrom) {
            "Account" -> {
                for (n in listAccount) {
                    n.checked = sts
                }
            }
            "Brand" -> {
                for (n in listBrands) {
                    n.checked = sts
                }
            }
            "Location" -> {
                for (n in listLocation) {
                    n.checked = sts
                }
            }
        }
    }

    private fun setAdapterData(isFrom: String) {
        when (isFrom) {
            "Account" -> {
                filterAdapter = FilterLocationAdapter(this, listAccount, isFrom)
            }
            "Brand" -> {
                filterAdapter = FilterLocationAdapter(this, listBrands, isFrom)
            }
            "Location" -> {
                filterAdapter = FilterLocationAdapter(this, listLocation, isFrom)
            }
        }
        recyclerView.adapter = filterAdapter
    }

    fun filter(text: String, isFrom: String) {
        when (isFrom) {
            "Account" -> {
                val temp: java.util.ArrayList<hierarchyData?> = java.util.ArrayList()
                for (d in listAccount) {
                    if (d.accountNumber.lowercase().contains(text.lowercase())) {
                        temp.add(d)
                    }
                }
                filterAdapter!!.updateList(temp)
            }
            "Brand" -> {
                val temp: java.util.ArrayList<brandNameListData?> = java.util.ArrayList()
                for (d in listBrands) {
                    if (d.brandName.lowercase().contains(text.lowercase())) {
                        temp.add(d)
                    }
                }
                filterAdapter!!.updateList(temp)
            }
            "Location" -> {
                val temp: java.util.ArrayList<locationNameListData?> = java.util.ArrayList()
                for (d in listLocation) {
                    if (d.locationName.lowercase().contains(text.lowercase())) {
                        temp.add(d)
                    }
                }
                filterAdapter!!.updateList(temp)
            }
        }
    }

    override fun onChecked(isFrom: String, pos: Int, check: Boolean) {
        when (isFrom) {
            "Account" -> {
                listAccount[pos].checked = check
            }
            "Brand" -> {
                listBrands[pos].checked = check
            }
            "Location" -> {
                listLocation[pos].checked = check
            }
        }
        setAdapterData(isFrom)
    }
}