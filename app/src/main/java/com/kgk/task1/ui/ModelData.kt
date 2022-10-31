package com.kgk.task1.ui

data class ResponseData(
    val status: String,
    val filterData: List<FilterData>,
)

data class FilterData(
    val companyName: String,
    val hierarchy: List<hierarchyData>,
)

data class hierarchyData(
    val accountNumber: String,
    var checked: Boolean = true,
    val brandNameList: List<brandNameListData>,
)

data class brandNameListData(
    val brandName: String,
    var checked: Boolean = true,
    val locationNameList: List<locationNameListData>,
)

data class locationNameListData(
    var checked: Boolean = true,
    val locationName: String,
)
