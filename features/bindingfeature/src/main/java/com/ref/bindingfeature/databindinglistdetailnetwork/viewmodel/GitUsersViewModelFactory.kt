package com.ref.bindingfeature.databindinglistdetailnetwork.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ref.bindingfeature.databindinglistdetailnetwork.dependency.DependencyUtils
import com.ref.bindingfeature.databindinglistdetailnetwork.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class GitUsersViewModelFactory : ViewModelProvider.Factory {
    lateinit var userRepository: UserRepository
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        userRepository = DependencyUtils.provideUserRepository()
        if (modelClass.isAssignableFrom(GitUserViewModel::class.java)) {
            return GitUserViewModel(Dispatchers.IO,userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}