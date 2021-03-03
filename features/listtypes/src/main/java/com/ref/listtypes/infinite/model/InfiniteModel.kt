package com.ref.listtypes.infinite.model

class InfiniteModel : ArrayList<InfiniteModel.InfiniteModelItem>(){
    data class InfiniteModelItem(
        val completed: Boolean, // false
        val id: Int, // 200
        val title: String, // ipsam aperiam voluptates qui
        val userId: Int // 10
    )
}