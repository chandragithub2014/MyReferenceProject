package com.ref.listtypes.infinite.viewmodels

import androidx.lifecycle.*
import com.ref.baselibrary.responsehelper.ResultOf
import com.ref.listtypes.expandable.models.ExpandableCountryModel
import com.ref.listtypes.expandable.models.StateCapital
import com.ref.listtypes.expandable.repository.CountryStateRepository
import com.ref.listtypes.infinite.model.InfiniteAdapterModel
import com.ref.listtypes.infinite.model.InfiniteModel
import com.ref.listtypes.infinite.repository.InfiniteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class InfiniteListViewModel(private val dispatcher: CoroutineDispatcher, private val infiniteRepository: InfiniteRepository) : ViewModel(),
    LifecycleObserver  {
    private val LOG_TAG = "InfiniteListViewModel"
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    private val _obtainInfiniteListResponse= MutableLiveData<ResultOf<InfiniteModel>>()
    val  obtainInfiniteListResponse: LiveData<ResultOf<InfiniteModel>> = _obtainInfiniteListResponse

    fun obtainInfiniteList(){
        loading.postValue(true)
        viewModelScope.launch(dispatcher){
            try{
                val infiniteListResponse = infiniteRepository.fetchInfiniteList()
                if(infiniteListResponse.isSuccessful){
                    loading.postValue(false)
                    val response = infiniteListResponse.body()
                    response?.let {
                        _obtainInfiniteListResponse.postValue(ResultOf.Success(it))
                    }
                }else{
                    loading.postValue(false)
                    _obtainInfiniteListResponse.postValue(ResultOf.Failure("Failed with Exception ", null))
                }
            }catch (e:Exception) {
                e.printStackTrace()
                _obtainInfiniteListResponse.postValue(ResultOf.Failure("Failed with Exception ${e.message} ", e))
            }

        }
    }

    fun prepareDataForAdapter(infiniteModel: InfiniteModel,startPosition:Int,size:Int): MutableList<InfiniteAdapterModel>{
        var adapterModel = mutableListOf<InfiniteAdapterModel>()
        for (item in startPosition until size) {
            adapterModel.add(InfiniteAdapterModel(InfiniteAdapterModel.VIEW_TYPE_DATA,infiniteModel[item]))
        }
        adapterModel.add(InfiniteAdapterModel(InfiniteAdapterModel.VIEW_TYPE_PROGRESS,InfiniteModel.InfiniteModelItem(false,0,"",0)))
        return adapterModel
    }
}