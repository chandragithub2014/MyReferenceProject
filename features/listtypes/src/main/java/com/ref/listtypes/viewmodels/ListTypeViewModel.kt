package com.ref.listtypes.viewmodels

import androidx.lifecycle.*
import com.ref.baselibrary.responsehelper.ResultOf
import com.ref.listtypes.expandable.models.StateCapital
import com.ref.listtypes.models.ListTypeModel
import com.ref.listtypes.repository.ListTypeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class ListTypeViewModel(private val dispatcher: CoroutineDispatcher, private val listTypeRepository: ListTypeRepository) : ViewModel(),
    LifecycleObserver  {
    private val LOG_TAG = "ListTypeViewModel"
    var loading: MutableLiveData<Boolean> = MutableLiveData()

    private val _obtainListTypeResponse= MutableLiveData<ResultOf<MutableList<ListTypeModel>>>()
    val  obtainListTypeResponse: LiveData<ResultOf<MutableList<ListTypeModel>>> = _obtainListTypeResponse

    fun obtainListTypeInfo() {
        loading.postValue(true)

        viewModelScope.launch(dispatcher) {
            var errorCode = -1
            try{
                var listTypeResponse = listTypeRepository.fetchListTypeInfo()
                listTypeResponse?.let {
                    loading.postValue(false)
                    _obtainListTypeResponse.postValue(ResultOf.Success(it))
                }
            }catch (e : Exception){
                e.printStackTrace()
                loading.postValue(false)
                if(errorCode != -1){
                    _obtainListTypeResponse.postValue(ResultOf.Failure("Failed with Error Code ${errorCode} ", e))
                }else{
                    _obtainListTypeResponse.postValue(ResultOf.Failure("Failed with Exception ${e.message} ", e))
                }
            }
        }
    }
}