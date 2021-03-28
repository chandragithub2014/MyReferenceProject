package com.ref.firebaseoprs.views

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ref.baselibrary.navigator.observeOnce
import com.ref.baselibrary.responsehelper.ResultOf
import com.ref.firebaseoprs.FirebaseoprsActivity
import com.ref.firebaseoprs.R
import com.ref.firebaseoprs.viewmodels.FireBaseViewModel
import com.ref.firebaseoprs.viewmodels.FireBaseViewModelFactory
import kotlinx.android.synthetic.main.fragment_login.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment(),LifecycleObserver {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var  fireBaseViewModel: FireBaseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       fireBaseViewModel =  (activity as FirebaseoprsActivity).fetchFireBaseViewModel()
   //   initViewModel()
        observerLoadingProgress()
       // observeSignIn()
        registration_btn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        login_btn.setOnClickListener {
            if(TextUtils.isEmpty(login_email.text.toString()) || TextUtils.isEmpty(login_pwd.text.toString())){
                Toast.makeText(requireContext(),"Login fields can't be empty",Toast.LENGTH_LONG).show()
            }else{
                signIn(login_email.text.toString(),login_pwd.text.toString())
            }
        }
    }

    private fun signIn(email:String, pwd:String){
        fireBaseViewModel.signIn(email,pwd)
        observeSignIn()
    }

    private fun observeSignIn(){
      fireBaseViewModel.signInStatus.observe(viewLifecycleOwner, Observer {result->
            result?.let {
                when(it){
                    is ResultOf.Success ->{
                        if(it.value.equals("Login Successful",ignoreCase = true)){
                            Toast.makeText(requireContext(),"Login Successful",Toast.LENGTH_LONG).show()
                            fireBaseViewModel.resetSignInLiveData()
                            navigateToTaxInfoFragment()
                        }else if(it.value.equals("Reset",ignoreCase = true)){

                        }
                        else{
                            Toast.makeText(requireContext(),"Login failed with ${it.value}",Toast.LENGTH_LONG).show()
                        }
                    }
                    is ResultOf.Failure -> {
                    val failedMessage =  it.message ?: "Unknown Error"
                    Toast.makeText(requireContext(),"Login failed with $failedMessage",Toast.LENGTH_LONG).show()
                   }
                }
            }
        })

        /*val signInStatusLiveData = fireBaseViewModel.signInStatus
        signInStatusLiveData.observeOnce(viewLifecycleOwner, Observer {
                result->
            result?.let {
                when(it){
                    is ResultOf.Success ->{
                        if(it.value.equals("Login Successful",ignoreCase = true)){
                            Toast.makeText(requireContext(),"Login Successful",Toast.LENGTH_LONG).show()
                            fireBaseViewModel.signInStatus.removeObservers(viewLifecycleOwner)
                            navigateToTaxInfoFragment()
                        }else{
                            Toast.makeText(requireContext(),"Login failed with ${it.value}",Toast.LENGTH_LONG).show()
                        }
                    }
                    is ResultOf.Failure -> {
                        val failedMessage =  it.message ?: "Unknown Error"
                        Toast.makeText(requireContext(),"Login failed with $failedMessage",Toast.LENGTH_LONG).show()
                    }
                }
            }

        })*/
    }

    private fun observerLoadingProgress(){
        fireBaseViewModel.fetchLoading().observe(viewLifecycleOwner, Observer {
            if (!it) {
                println(it)
                login_progress.visibility = View.GONE
            }else{
                login_progress.visibility = View.VISIBLE
            }

        })


    }

    private fun navigateToTaxInfoFragment(){
        findNavController().navigate(R.id.action_loginFragment_to_employeeTaxInfoFragment, bundleOf("email" to login_email.text.toString()))
    }
    private fun initViewModel(){
        var fireBaseViewModelFactory = FireBaseViewModelFactory()
        fireBaseViewModel = ViewModelProvider(this,fireBaseViewModelFactory).get(FireBaseViewModel::class.java)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}