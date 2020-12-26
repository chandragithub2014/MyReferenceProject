package com.ref.listtypes.expandable.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ref.listtypes.expandable.repository.CountryStateRepository
import kotlinx.coroutines.Dispatchers

class CountryStateViewModelFactory : ViewModelProvider.Factory{

    lateinit var countryStateRepository: CountryStateRepository
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        countryStateRepository = CountryStateRepository()
        if (modelClass.isAssignableFrom(CountryStateViewModel::class.java)) {
            return CountryStateViewModel(Dispatchers.IO,countryStateRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}