package com.ref.listtypes.infinite.interfaces

import com.ref.listtypes.infinite.model.InfiniteAdapterModel
import com.ref.listtypes.infinite.model.InfiniteModel

interface LoadMore {
    fun loadMore(startPosition:Int,endPosition:Int,previousList:MutableList<InfiniteAdapterModel>, originalModel: InfiniteModel)
}