package com.ref.bindingfeature.utils

import com.ref.bindingfeature.model.BindingModel

object DependencyUtils {

    fun prepareBindingMutableList():MutableList<BindingModel>{
        var bindingList = mutableListOf<BindingModel>()
        var bindingModel = BindingModel("ViewBinding")
        bindingList.add(bindingModel)
        bindingModel = BindingModel("DataBindingForm")
        bindingList.add(bindingModel)
        bindingModel = BindingModel("DataBindingList/Detail")
        bindingList.add(bindingModel)
        bindingModel = BindingModel("RoomDataBinding")
        bindingList.add(bindingModel)
        bindingModel = BindingModel("NetworkDataBindingList")
        bindingList.add(bindingModel)
        return bindingList
    }
}