package com.ref.bindingfeature.viewbinding.views

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ref.baselibrary.navigator.hideKeyboard
import com.ref.baselibrary.navigator.toastLong
import com.ref.bindingfeature.databinding.FragmentLoginViewBindingBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginViewBindingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginViewBindingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding:FragmentLoginViewBindingBinding? = null
    private val binding get() = _binding!!
    //fragment_login_view_binding
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

        _binding = FragmentLoginViewBindingBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            binding.loginBtn.hideKeyboard()
            if (!TextUtils.isEmpty(binding.usernameEd.text.toString()) &&
                !TextUtils.isEmpty(binding.passwordEd.text.toString())
            ) {
              "Login with userName ${binding.usernameEd.text.toString()} is Successful ".toastLong(this@LoginViewBindingFragment.requireActivity())
            }else{
              "Please Enter Valid Info in the Fields".toastLong(this@LoginViewBindingFragment.requireActivity())
            }
        }

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginViewBindingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginViewBindingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}