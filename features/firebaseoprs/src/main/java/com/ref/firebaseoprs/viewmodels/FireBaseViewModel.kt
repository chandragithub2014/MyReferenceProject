package com.ref.firebaseoprs.viewmodels

import android.net.Uri
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.*
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.ref.baselibrary.responsehelper.ResultOf
import com.ref.firebaseoprs.models.TaxInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.*


class FireBaseViewModel(private val dispatcher: CoroutineDispatcher) : ViewModel(),
    LifecycleObserver {
    private val LOG_TAG = "FireBaseViewModel"
    private var  auth: FirebaseAuth? = null
    private var storage: FirebaseStorage
    private var storageReference: StorageReference
    private  var rootNode: FirebaseDatabase
    private  var reference: DatabaseReference
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    init {

        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference
        rootNode = FirebaseDatabase.getInstance()
        reference = rootNode.getReference("taxInfo")

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

    private val _signInStatus = MutableLiveData<ResultOf<String>>()
    val signInStatus: LiveData<ResultOf<String>> = _signInStatus
    fun signIn(email:String, password:String){
        loading.postValue(true)
        viewModelScope.launch(dispatcher){
            var  errorCode = -1
            try{
                auth?.let{ login->
                    login.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener {task: Task<AuthResult> ->

                            if(!task.isSuccessful){
                                println("Login Failed with ${task.exception}")
                                _signInStatus.postValue(ResultOf.Success("Login Failed with ${task.exception}"))
                            }else{
                                _signInStatus.postValue(ResultOf.Success("Login Successful"))

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

    fun resetSignInLiveData(){
        _signInStatus.value =  ResultOf.Success("Reset")
    }
    private val _signOutStatus = MutableLiveData<ResultOf<String>>()
    val signOutStatus: LiveData<ResultOf<String>> = _signOutStatus
    fun signOut(){
        loading.postValue(true)
        viewModelScope.launch(dispatcher){
            var  errorCode = -1
            try{
                auth?.let {authentation ->
                    authentation.signOut()
                    _signOutStatus.postValue(ResultOf.Success("Signout Successful"))
                    loading.postValue(false)
                }

            }catch (e:Exception){
                e.printStackTrace()
                loading.postValue(false)
                if(errorCode != -1){
                    _signOutStatus.postValue(ResultOf.Failure("Failed with Error Code ${errorCode} ", e))
                }else{
                    _signOutStatus.postValue(ResultOf.Failure("Failed with Exception ${e.message} ", e))
                }


            }
        }
    }

    private val _uploadDocResult = MutableLiveData<ResultOf<String>>()
    val uploadDocResult: LiveData<ResultOf<String>> = _uploadDocResult
    fun uploadDoc(selectedImageUri: Uri){
        loading.postValue(true)
        viewModelScope.launch(dispatcher){
            var  errorCode = -1
            try{
                val ref = storageReference
                    .child("/uploadedDocs/"
                            + UUID.randomUUID().toString())
                val uploadTask = ref.putFile(selectedImageUri)
                val urlTask = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref.downloadUrl
                })?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        println("Downloaded URL: is ${downloadUri.toString()}")
                        var downloadUrl = downloadUri.toString()
                        _uploadDocResult.postValue(ResultOf.Success(downloadUrl))
                        loading.postValue(false)
                        // addUploadRecordToDb(downloadUri.toString())
                    } else {
                        _uploadDocResult.postValue(ResultOf.Success("Upload Failed"))
                        loading.postValue(false)
                    }
                }?.addOnFailureListener{
                    _uploadDocResult.postValue(ResultOf.Success("Upload Failed"))
                    loading.postValue(false)
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

    private val _saveResult = MutableLiveData<ResultOf<String>>()
    val saveResult: LiveData<ResultOf<String>> = _saveResult
   fun saveTaxDetails(taxInfo: TaxInfo){
       loading.postValue(true)
       viewModelScope.launch(dispatcher){
           var  errorCode = -1
           try{
               val id: String? = reference.push().key


               reference.addValueEventListener(object : ValueEventListener {
                   override fun onDataChange(@NonNull snapshot: DataSnapshot) {
                       // inside the method of on Data change we are setting
                       // our object class to our database reference.
                       // data base reference will sends data to firebase.
                       if (id != null) {
                           reference.child(id).setValue(taxInfo)
                       }

                       _saveResult.postValue(ResultOf.Success("Data Saved Successfully"))
                       loading.postValue(false)
                   }

                   override fun onCancelled(@NonNull error: DatabaseError) {
                       // if the data is not added or it is cancelled then
                       // we are displaying a failure toast message.
                       _saveResult.postValue(ResultOf.Success("Data Save Failed"))
                       loading.postValue(false)
                   }
               })
           }catch (e:Exception){
               e.printStackTrace()
               loading.postValue(false)
               if(errorCode != -1){
                   _saveResult.postValue(ResultOf.Failure("Failed with Error Code ${errorCode} ", e))
               }else{
                   _saveResult.postValue(ResultOf.Failure("Failed with Exception ${e.message} ", e))
               }


           }
       }
   }

    fun fetchLoading():LiveData<Boolean> = loading



    /*
    Registration Failed with com.google.firebase.FirebaseException: An internal error has occurred. [ Unable to resolve host "www.googleapis.com":No address associated with hostname ]
     */
}