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
import com.ref.baselibrary.dialogs.CustomAlertDialog
import com.ref.baselibrary.dialogs.DialogListener
import com.ref.baselibrary.dialogs.DialogModel
import com.ref.baselibrary.dialogs.setDate
import com.ref.baselibrary.navigator.hideKeyboard
import com.ref.roomdboprs.R
import com.ref.roomdboprs.model.User
import kotlinx.android.synthetic.main.fragment_edit_bill.*
import kotlinx.android.synthetic.main.fragment_edit_bill.ed_date
import kotlinx.android.synthetic.main.fragment_edit_bill.ed_place_of_visit
import kotlinx.android.synthetic.main.fragment_edit_bill.ed_price
import kotlinx.android.synthetic.main.fragment_edit_bill.ed_purpose_visit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditBillFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditBillFragment : Fragment() , DialogListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var editView : View
    lateinit var userViewModel: UserViewModel
     private var uid : Long = 0
    private var userName : String? = null
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
        editView =  inflater.inflate(R.layout.fragment_edit_bill, container, false)
        return editView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uid = arguments?.getLong("UID",-1)
        enableDisableViews(isEnabled = false)
        if(uid!=null && uid > 0){
            userViewModel.fetchUserBillDetailsByUID(uid).observe(viewLifecycleOwner, Observer {
                it?.let {
                    this@EditBillFragment.uid = it.uid
                    this.userName = it.userName
                    populateUserInterface(it)
                }
            })
        }
        edit_btn.setOnClickListener {
            if(edit_btn.text.toString().equals("Edit", ignoreCase = true)){
                enableDisableViews(isEnabled = true)
                edit_btn.text = "Save"
            }else{
                enableDisableViews(isEnabled = false)
                edit_btn.text = "Edit"
                updateBill()
            }

        }

        delete.setOnClickListener {
            showCustomAlertDialog(generateDialogModel("delete"))
        }
        ed_date.setOnClickListener {
            ed_date.hideKeyboard()
            setDate(this@EditBillFragment.requireActivity(),ed_date)
        }
        observeViewModel()

    }

    private fun updateBill(){
     if(uid > 0 && userName!=null){
         if(!TextUtils.isEmpty(ed_place_of_visit.text.toString())
             && !TextUtils.isEmpty(ed_purpose_visit.text.toString())
             && !TextUtils.isEmpty(ed_price.text.toString())
             && !TextUtils.isEmpty(ed_date.text.toString())){
            userViewModel.updateUser(
                User(uid,userName,ed_place_of_visit.text.toString(),
                ed_purpose_visit.text.toString(),
                ed_price.text.toString().toLong(), ed_date.text.toString()
            )
            )
         }
     }
    }
    private fun initViewModel(){
        var userViewModelFactory = UserViewModelFactory()
        userViewModel = ViewModelProvider(this,userViewModelFactory).get(UserViewModel::class.java)
        // placeHolderViewModel.fetchPostsFromService()


    }

    private fun populateUserInterface(user : User){
       ed_place_of_visit.setText(user.placeVisited)
        ed_purpose_visit.setText(user.visitPurpose)
        ed_price.setText(user.visitExpense.toString())
        ed_date.setText(user.visitedDate)
    }

    private fun enableDisableViews(isEnabled : Boolean){
        ed_place_of_visit.isEnabled = isEnabled
        ed_purpose_visit.isEnabled = isEnabled
        ed_price.isEnabled = isEnabled
        ed_date.isEnabled = isEnabled
        ed_place_of_visit.isClickable = isEnabled
        ed_purpose_visit.isClickable = isEnabled
        ed_price.isClickable = isEnabled
        ed_date.isClickable = isEnabled
    }
    private  fun observeViewModel(){
        userViewModel.updatedRowIdLiveData().observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it > 0){
                    Toast.makeText(this@EditBillFragment.requireActivity(),"Transaction Updated Successfully",
                        Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()

                }else{
                    Toast.makeText(this@EditBillFragment.requireActivity(),"Transaction Failed to Update",
                        Toast.LENGTH_LONG).show()
                }
            }
        })

        userViewModel.deletedRowIdLiveData().observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it > 0){
                    Toast.makeText(this@EditBillFragment.requireActivity(),"Transaction Deleted Successfully",
                        Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()

                }else{
                    Toast.makeText(this@EditBillFragment.requireActivity(),"Transaction Failed to Delete",
                        Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun showCustomAlertDialog(dialogModel: DialogModel){
        CustomAlertDialog.showAlertDialog(dialogModel,this)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditBillFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditBillFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onPositiveButtonClick(actionType: String) {
        when(actionType){
            "delete" -> {
                if(uid > 0){
                  userViewModel.deleteUser(uid)
                }
            }
        }
    }

    override fun onNegativeButtonClick(actionType: String) {

    }

    private fun generateDialogModel(actionType: String) : DialogModel {
        when (actionType) {
            "delete" -> {
                return DialogModel(
                    title = "My Bills",
                    content = "Are you sure you want to delete?",
                    positiveButton = "OK",
                    negativeButton = "Cancel",
                    actionType = actionType,
                    context = requireActivity(),
                    activityContext = requireActivity()
                )
            }
            else -> {
                return DialogModel(
                    title = "My Bills",
                    content = "Are you sure you want to delete?",
                    positiveButton = "OK",
                    negativeButton = "Cancel",
                    actionType = actionType,
                    context = requireActivity(),
                    activityContext = requireActivity()
                )
            }
        }
    }
}