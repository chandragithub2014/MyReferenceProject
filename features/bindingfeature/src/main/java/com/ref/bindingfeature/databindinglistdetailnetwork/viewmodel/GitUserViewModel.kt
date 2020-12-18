package com.ref.bindingfeature.databindinglistdetailnetwork.viewmodel

import androidx.lifecycle.*
import com.ref.bindingfeature.databindinglistdetailnetwork.model.GitUserModel
import com.ref.bindingfeature.databindinglistdetailnetwork.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class GitUserViewModel(private val dispatcher: CoroutineDispatcher, private val userRepository: UserRepository) : ViewModel(),
    LifecycleObserver {
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    private val errorOnAPI = MutableLiveData<String>()
    private val errorMessage = MutableLiveData<String>()
    var gitUserMutableLiveData  = MutableLiveData<GitUserModel>()


    fun fetchUsersFromRepository(){
        viewModelScope.launch(dispatcher){
            try{
                loading.postValue(true)
                val userResponseFromRepository = userRepository.fetchUsers()
                if(userResponseFromRepository.isSuccessful){
                    gitUserMutableLiveData.postValue(userResponseFromRepository.body())
                    loading.postValue(false)
                }else{
                    println("Received Response::::")
                    println("Response code ${userResponseFromRepository.code()}")
                    println("Response Error body is  ${userResponseFromRepository.errorBody()}")
                    println("Response Error Message is  ${userResponseFromRepository.message()}")
                }
            } catch (e:java.net.UnknownHostException){
                //     errorMessage.postValue(colruyt.android.dsa.ui_components.R.string.unknownhost_error_message.toString())
                e.printStackTrace()
                loading.postValue(false)


            }
            catch (e:Exception){
                e.printStackTrace()
                loading.postValue(false)


            }
        }
    }

    fun fetchUsersLiveData(): LiveData<GitUserModel> = gitUserMutableLiveData
    fun fetchLoadStatus(): LiveData<Boolean> = loading
}