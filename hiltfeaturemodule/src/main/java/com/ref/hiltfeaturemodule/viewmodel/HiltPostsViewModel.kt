package com.ref.hiltfeaturemodule.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ref.baselibrary.responsehelper.ResultOf
import com.ref.hiltfeaturemodule.model.PlaceHolderPostsDataModel
import com.ref.hiltfeaturemodule.repository.PlaceHolderRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class HiltPostsViewModel @ViewModelInject constructor(private val dispatcher: CoroutineDispatcher, private val networkRepository: PlaceHolderRepository) :
    ViewModel(), LifecycleObserver {

    var loading: MutableLiveData<Boolean> = MutableLiveData()
    val loadingLiveData: LiveData<Boolean> = loading
    private val _postsResponse= MutableLiveData<ResultOf<PlaceHolderPostsDataModel>>()
    val  postsResponse: LiveData<ResultOf<PlaceHolderPostsDataModel>> = _postsResponse

    fun fetchPosts(){
        viewModelScope.launch(dispatcher){
            var errorCode = -1
            try {
                var postsResponseFromAPI = networkRepository.fetchPosts()
                if (postsResponseFromAPI.isSuccessful) {
                    loading.postValue(false)
                    val response = postsResponseFromAPI.body()
                    response?.let {
                        _postsResponse.postValue(ResultOf.Success(it))
                    }

                } else {
                    loading.postValue(false)
                    println("Received Response::::")
                    println("Response code ${postsResponseFromAPI.code()}")
                    println("Response Error body is  ${postsResponseFromAPI.errorBody()}")
                    println("Response Error Message is  ${postsResponseFromAPI.message()}")

                    errorCode = postsResponseFromAPI.code()
                    loading.postValue(false)
                    if (errorCode != -1) {
                        _postsResponse.postValue(
                            ResultOf.Failure(
                                "Failed with Error Code ${errorCode} ",
                                null
                            )
                        )
                    } else {
                        _postsResponse.postValue(ResultOf.Failure("Failed with Exception  ", null))
                    }

                }

            }catch (e : Exception){
                loading.postValue(false)
                e.printStackTrace()
                if(errorCode != -1){
                    _postsResponse.postValue(ResultOf.Failure("Failed with Error Code ${errorCode} ", e))
                }else{
                    _postsResponse.postValue(ResultOf.Failure("Failed with Exception ${e.message} ", e))
                }
            }
        }

    }
}