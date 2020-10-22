package com.ref.bindingfeature.databindingrecyclerview.repository

import colruyt.android.dsa.rayon.network.GitUserServiceAPI
import com.ref.bindingfeature.databindingrecyclerview.model.GitUserModel
import retrofit2.Response

class UserRepository(private  val apiServiceAPI: GitUserServiceAPI) {

   suspend fun fetchUsers(): Response<GitUserModel> = apiServiceAPI.fetchUsers()
}