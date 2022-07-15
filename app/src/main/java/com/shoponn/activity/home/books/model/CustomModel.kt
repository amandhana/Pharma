package com.shoponn.activity.home.books.model

import com.shoponn.activity.home.model.sub_cat.books.SubCategoryResponse

class CustomModel {
    var mainCat = ""
    var subCatList : List<SubCategoryResponse?>? = null

    @JvmName("getMainCat1")
    fun getMainCat() : String{
        return mainCat
    }
    @JvmName("setMainCat1")
    fun setMainCat(mainCat : String){
        this.mainCat = mainCat
    }
    @JvmName("getSubCatList1")
    fun getSubCatList() : List<SubCategoryResponse?>?{
        return subCatList
    }
    @JvmName("setMainCat1")
    fun setSubCatList(subCatList: List<SubCategoryResponse?>?){
        this.subCatList = subCatList
    }
}