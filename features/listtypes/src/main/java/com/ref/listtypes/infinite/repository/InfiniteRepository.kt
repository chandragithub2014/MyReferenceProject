package com.ref.listtypes.infinite.repository

import com.ref.listtypes.expandable.models.StateCapital
import com.ref.listtypes.infinite.model.InfiniteModel
import com.ref.listtypes.utils.JSONHelper
import retrofit2.Response

class InfiniteRepository {
    suspend  fun fetchInfiniteList() : Response<InfiniteModel> = JSONHelper.fetchParsedJSONInfoForInfiniteList()
}