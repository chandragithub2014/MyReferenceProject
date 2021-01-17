package com.ref.hiltfeaturemodule.repository


import com.ref.hiltfeaturemodule.model.PlaceHolderPostsDataModel
import com.ref.hiltfeaturemodule.network.PlaceHolderServiceAPI
import retrofit2.Response
import javax.inject.Inject

class PlaceHolderRepository  @Inject constructor (private  val apiServiceAPI: PlaceHolderServiceAPI) {

    suspend fun fetchPosts(): Response<PlaceHolderPostsDataModel> = apiServiceAPI.fetchPlaceHolderPosts()

}