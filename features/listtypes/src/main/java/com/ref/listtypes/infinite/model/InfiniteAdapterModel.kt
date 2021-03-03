package com.ref.listtypes.infinite.model

import com.ref.listtypes.expandable.models.StateCapital

class InfiniteAdapterModel {
    companion object{
        const val VIEW_TYPE_DATA  = 0
        const val VIEW_TYPE_PROGRESS  =1

    }

    var infiniteModelItem: InfiniteModel.InfiniteModelItem
    var type : Int

    constructor( type : Int, infiniteModelItem: InfiniteModel.InfiniteModelItem){
        this.type = type
        this.infiniteModelItem = infiniteModelItem
    }
}