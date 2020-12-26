package com.ref.listtypes.utils

import com.ref.listtypes.models.ListTypeModel

object ListTypeUtils {

    fun fetchListTypeInfo():MutableList<ListTypeModel> {
        var listTypesList = mutableListOf<ListTypeModel>()
        var listTypeModel = ListTypeModel("ExpandableRecyclerView")
        listTypesList.add(listTypeModel)
        listTypeModel = ListTypeModel("SectionedRecyclerView")
        listTypesList.add(listTypeModel)
        return listTypesList
    }
}