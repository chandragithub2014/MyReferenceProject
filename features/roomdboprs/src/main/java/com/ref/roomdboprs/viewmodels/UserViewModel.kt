package com.ref.roomdboprs.viewmodels

import androidx.lifecycle.*
import com.ref.roomdboprs.model.User
import com.ref.roomdboprs.repository.UserDBRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch


class UserViewModel(private val dispatcher: CoroutineDispatcher, private val userDBRepository: UserDBRepository) : ViewModel(),LifecycleObserver{

    private val insertedRowId:MutableLiveData<Long> = MutableLiveData<Long>()
    private val updatedRowId:MutableLiveData<Int> = MutableLiveData<Int>()
    private val deletedRowId:MutableLiveData<Int> = MutableLiveData<Int>()
    fun fetchUserBillDetails(userId : String) : LiveData<List<User>> {
        return userDBRepository.fetchUserBillDetails(userId).asLiveData()
    }

    fun fetchUserBillDetailsByUID(uid : Long) : LiveData<User> {
        return userDBRepository.fetchUserBillDetailsByUID(uid).asLiveData()
    }

    fun insertUser(user:User) {
        var insertedId:Long = -1
        viewModelScope.launch(dispatcher) {
           try {
               insertedId = userDBRepository.insertUserInfo(user)
               insertedRowId.postValue(insertedId)
           }
           catch (e : Exception){
               e.printStackTrace()
               insertedRowId.postValue(insertedId)
           }
        }
    }

    fun updateUser(user : User){
        var updatedId:Int = -1
        viewModelScope.launch(dispatcher) {
            try {
                updatedId = userDBRepository.updateUser(user)
                updatedRowId.postValue(updatedId)
            }
            catch (e : Exception){
                e.printStackTrace()
                updatedRowId.postValue(updatedId)
            }
        }
    }

    fun deleteUser(uid : Long){
        var deletedId:Int = -1
        viewModelScope.launch(dispatcher) {
            try {
                deletedId = userDBRepository.deleteUserInfo(uid)
                deletedRowId.postValue(deletedId)
            }
            catch (e : Exception){
                e.printStackTrace()
                deletedRowId.postValue(deletedId)
            }
        }
    }

    fun fetchExpenseTotal(userId : String) : LiveData<Long>{
        return userDBRepository.fetchTotalExpenses(userId).asLiveData()
    }

    fun insertedRowIdLiveData() = insertedRowId
    fun updatedRowIdLiveData() = updatedRowId
    fun deletedRowIdLiveData() = deletedRowId
}