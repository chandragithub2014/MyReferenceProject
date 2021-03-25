package com.ref.firebaseoprs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ref.firebaseoprs.viewmodels.FireBaseViewModel
import com.ref.firebaseoprs.viewmodels.FireBaseViewModelFactory

class FirebaseoprsActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var  fireBaseViewModel: FireBaseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebaseoprs)
       // initFireBaseAuth()
       // authenticateCredentials()
        initViewModel()
    }

    private fun initViewModel(){
        var fireBaseViewModelFactory = FireBaseViewModelFactory()
        fireBaseViewModel = ViewModelProvider(this,fireBaseViewModelFactory).get(FireBaseViewModel::class.java)
    }

    private fun initFireBaseAuth(){
        auth = Firebase.auth
    }
/*
ab@gmail.com/abcdef



 */
    internal fun  fetchFireBaseViewModel() = fireBaseViewModel
    private fun authenticateCredentials(){
        val email = "a@gmail.com"
        val password = "abcdef"
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
               if(task.isSuccessful){
                   val user = auth.currentUser
                   println("Authenticated User is ${user.toString()}")
               }else{
                   Log.w(TAG, "signInWithEmail:failure", task.exception)
                   Toast.makeText(baseContext, "Authentication failed.",
                       Toast.LENGTH_LONG).show()
                   println("Exception is ${task.exception!!}")
               }
                if (!task.isSuccessful) {
                    println("Authentication Failed....")
                }

            }
    }

    companion object {
        private val TAG = "FirebaseoprsActivity"
    }
}