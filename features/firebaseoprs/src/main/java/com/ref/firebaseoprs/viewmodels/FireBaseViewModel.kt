package com.ref.firebaseoprs.viewmodels

import androidx.lifecycle.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.ref.baselibrary.responsehelper.ResultOf
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch


class FireBaseViewModel(private val dispatcher: CoroutineDispatcher) : ViewModel(),
    LifecycleObserver {
    private val LOG_TAG = "FireBaseViewModel"
    private var  auth: FirebaseAuth? = null
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    init {

        auth = FirebaseAuth.getInstance();

    }
    private val _registrationStatus = MutableLiveData<ResultOf<String>>()
    val registrationStatus: LiveData<ResultOf<String>> = _registrationStatus
    fun signUp(email:String, password:String){
        loading.postValue(true)
        viewModelScope.launch(dispatcher){
            var  errorCode = -1
            try{
                 auth?.let { authentication ->
                     authentication.createUserWithEmailAndPassword(email,password)
                         .addOnCompleteListener {task: Task<AuthResult> ->
                             if(!task.isSuccessful){
                                 println("Registration Failed with ${task.exception}")
                                 _registrationStatus.postValue(ResultOf.Success("Registration Failed with ${task.exception}"))
                             }else{
                                 _registrationStatus.postValue(ResultOf.Success("UserCreated"))

                             }
                             loading.postValue(false)
                         }

                 }
            }catch (e:Exception){
                e.printStackTrace()
                loading.postValue(false)
                if(errorCode != -1){
                    _registrationStatus.postValue(ResultOf.Failure("Failed with Error Code ${errorCode} ", e))
                }else{
                    _registrationStatus.postValue(ResultOf.Failure("Failed with Exception ${e.message} ", e))
                }


            }
        }
    }


    /*
    Registration Failed with com.google.firebase.FirebaseException: An internal error has occurred. [ Unable to resolve host "www.googleapis.com":No address associated with hostname ]
     */
}