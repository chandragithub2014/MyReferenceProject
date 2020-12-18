package com.ref.roomdboprs.views

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ref.roomdboprs.viewmodels.UserViewModel
import com.ref.roomdboprs.viewmodels.UserViewModelFactory
import com.ref.baselibrary.dialogs.setDate
import com.ref.baselibrary.navigator.hideKeyboard
import com.ref.roomdboprs.R
import com.ref.roomdboprs.model.User
import kotlinx.android.synthetic.main.fragment_add_bill.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddBillFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddBillFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var addBillView : View
    private var userName : String? = null
    lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addBillView =  inflater.inflate(R.layout.fragment_add_bill, container, false)
        return  addBillView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userName  = arguments?.getString("userName","")
        add_transaction.setOnClickListener {
            if(!TextUtils.isEmpty(ed_place_of_visit.text.toString())
                && !TextUtils.isEmpty(ed_purpose_visit.text.toString())
                && !TextUtils.isEmpty(ed_price.text.toString())
                && !TextUtils.isEmpty(ed_date.text.toString())){
                userViewModel.insertUser(
                    User(0,userName,ed_place_of_visit.text.toString(),
                ed_purpose_visit.text.toString(),
                ed_price.text.toString().toLong(), ed_date.text.toString()
                )
                )
            }
        }
        ed_date.setOnClickListener {
            ed_date.hideKeyboard()
            setDate(this@AddBillFragment.requireActivity(),ed_date)
        }
        observeViewModel()
    }

    private  fun observeViewModel(){
        userViewModel.insertedRowIdLiveData().observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it > 0){
                    Toast.makeText(this@AddBillFragment.requireActivity(),"Transaction Added Successfully",Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()

                }else{
                    Toast.makeText(this@AddBillFragment.requireActivity(),"Transaction Failed to Add",Toast.LENGTH_LONG).show()
                }
            }
        })
    }
    private fun initViewModel(){
        var userViewModelFactory = UserViewModelFactory()
        userViewModel = ViewModelProvider(this,userViewModelFactory).get(UserViewModel::class.java)
        // placeHolderViewModel.fetchPostsFromService()


    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddBillFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddBillFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}