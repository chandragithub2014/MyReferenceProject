package com.ref.mvvmfeature.repository


import com.ref.mvvmfeature.model.PlaceHolderCommentsModel
import com.ref.mvvmfeature.model.PlaceHolderPostsDataModel
import com.ref.mvvmfeature.network.PlaceHolderServiceAPI
import retrofit2.Response

class PlaceHolderRepository(private  val apiServiceAPI: PlaceHolderServiceAPI) {

   suspend fun fetchPosts(): Response<PlaceHolderPostsDataModel> = apiServiceAPI.fetchPlaceHolderPosts()
   suspend fun fetchComments(): Response<PlaceHolderCommentsModel> = apiServiceAPI.fetchPlaceHolderComments()
}