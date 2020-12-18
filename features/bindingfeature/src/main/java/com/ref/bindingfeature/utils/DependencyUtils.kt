package com.ref.bindingfeature.utils

import com.ref.bindingfeature.model.BindingModel

object DependencyUtils {

    fun prepareBindingMutableList():MutableList<BindingModel>{
        var bindingList = mutableListOf<BindingModel>()
        var bindingModel = BindingModel("ViewBinding")
        bindingList.add(bindingModel)
        bindingModel = BindingModel("DataBindingList/Detail+Network")
        bindingList.add(bindingModel)
        return bindingList
    }
}