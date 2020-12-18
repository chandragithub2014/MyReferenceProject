package com.ref.roomdboprs.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ref.roomdboprs.dependency.DependencyUtils
import com.ref.roomdboprs.repository.UserDBRepository
import kotlinx.coroutines.Dispatchers


class UserViewModelFactory : ViewModelProvider.Factory {
    lateinit var userDBRepository: UserDBRepository
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        userDBRepository = DependencyUtils.fetchUserRepository()
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(Dispatchers.IO,userDBRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}