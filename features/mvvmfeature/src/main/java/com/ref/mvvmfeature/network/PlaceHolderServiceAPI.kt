package com.ref.mvvmfeature.network

import com.ref.mvvmfeature.model.PlaceHolderCommentsModel
import com.ref.mvvmfeature.model.PlaceHolderPostsDataModel
import retrofit2.Response
import retrofit2.http.GET


interface PlaceHolderServiceAPI {

    @GET("posts")
   suspend fun fetchPlaceHolderPosts(): Response<PlaceHolderPostsDataModel>


    @GET("comments")
    suspend fun fetchPlaceHolderComments() : Response<PlaceHolderCommentsModel>
}