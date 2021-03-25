package com.ref.firebaseoprs.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers

class FireBaseViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(FireBaseViewModel::class.java)) {
            return FireBaseViewModel(Dispatchers.IO) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}