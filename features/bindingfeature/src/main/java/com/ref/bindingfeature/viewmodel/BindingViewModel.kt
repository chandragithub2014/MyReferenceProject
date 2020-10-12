package com.ref.bindingfeature.viewmodel

import androidx.lifecycle.*
import com.ref.bindingfeature.model.BindingModel
import com.ref.bindingfeature.utils.DependencyUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class BindingViewModel(private val dispatcher: CoroutineDispatcher) : ViewModel() ,
    LifecycleObserver {
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var bindingListMutableLiveData  = MutableLiveData<MutableList<BindingModel>>()


    fun fetchBindingListFromUtils(){
        viewModelScope.launch(dispatcher) {
            bindingListMutableLiveData.postValue(DependencyUtils.prepareBindingMutableList())
        }
    }

    fun fetchBindingListLiveData(): LiveData<MutableList<BindingModel>> = bindingListMutableLiveData
    fun fetchLiveLoadingStatus():LiveData<Boolean> = loading
}