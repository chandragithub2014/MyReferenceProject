package com.ref.firebaseoprs.views

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.ref.baselibrary.responsehelper.ResultOf
import com.ref.firebaseoprs.FirebaseoprsActivity
import com.ref.firebaseoprs.R
import com.ref.firebaseoprs.viewmodels.FireBaseViewModel
import kotlinx.android.synthetic.main.fragment_registration.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegistrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistrationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var registrationFragmentView:View
    private lateinit var  fireBaseViewModel: FireBaseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        fireBaseViewModel =  (activity as FirebaseoprsActivity).fetchFireBaseViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        registrationFragmentView =  inflater.inflate(R.layout.fragment_registration, container, false)
        return registrationFragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        register_btn.setOnClickListener {
         if(TextUtils.isEmpty(editTextTextEmailAddress.text.toString()) || TextUtils.isEmpty(editTextTextPassword.text.toString())
             || TextUtils.isEmpty(editTextTextPassword2.text.toString())){
             Toast.makeText(requireContext(),"Input Fields cannot be Empty",Toast.LENGTH_LONG).show()
         }else if(editTextTextPassword.text.toString() != editTextTextPassword2.text.toString()){
             Toast.makeText(requireContext(),"Passwords don't match",Toast.LENGTH_LONG).show()
         }else{
             doRegistration()
         }


        }

        observeRegistration()
    }

    private fun doRegistration(){
        fireBaseViewModel.signUp(editTextTextEmailAddress.text.toString(),editTextTextPassword.text.toString())
    }

    private fun observeRegistration(){
        fireBaseViewModel.registrationStatus.observe(viewLifecycleOwner, Observer {result->
            result?.let {
                when(it){
                    is ResultOf.Success ->{
                        if(it.value.equals("UserCreated",ignoreCase = true)){
                            Toast.makeText(requireContext(),"Registration Successful User created",Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(requireContext(),"Registration failed with ${it.value}",Toast.LENGTH_LONG).show()
                        }
                    }

                    is ResultOf.Failure -> {
                        val failedMessage =  it.message ?: "Unknown Error"
                        Toast.makeText(requireContext(),"Registration failed with $failedMessage",Toast.LENGTH_LONG).show()
                    }
                }
            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegistrationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistrationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}