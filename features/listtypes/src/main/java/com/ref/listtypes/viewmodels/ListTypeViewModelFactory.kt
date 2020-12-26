package com.ref.listtypes.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ref.listtypes.expandable.repository.CountryStateRepository
import com.ref.listtypes.expandable.viewmodels.CountryStateViewModel
import com.ref.listtypes.repository.ListTypeRepository
import kotlinx.coroutines.Dispatchers

class ListTypeViewModelFactory : ViewModelProvider.Factory {
    lateinit var listTypeRepository: ListTypeRepository
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        listTypeRepository = ListTypeRepository()
        if (modelClass.isAssignableFrom(ListTypeViewModel::class.java)) {
            return ListTypeViewModel(Dispatchers.IO,listTypeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}