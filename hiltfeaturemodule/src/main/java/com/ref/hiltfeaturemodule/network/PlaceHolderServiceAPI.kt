package com.ref.hiltfeaturemodule.network


import com.ref.hiltfeaturemodule.model.PlaceHolderPostsDataModel
import retrofit2.Response
import retrofit2.http.GET


interface PlaceHolderServiceAPI {

    @GET("posts")
   suspend fun fetchPlaceHolderPosts(): Response<PlaceHolderPostsDataModel>


    /*@GET("comments")
    suspend fun fetchPlaceHolderComments() : Response<PlaceHolderCommentsModel>*/
}