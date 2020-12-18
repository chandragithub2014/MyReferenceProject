package com.ref.bindingfeature.databindinglistdetailnetwork.repository

import colruyt.android.dsa.rayon.network.GitUserServiceAPI
import com.ref.bindingfeature.databindinglistdetailnetwork.model.GitUserModel
import retrofit2.Response

class UserRepository(private  val apiServiceAPI: GitUserServiceAPI) {

   suspend fun fetchUsers(): Response<GitUserModel> = apiServiceAPI.fetchUsers()
}