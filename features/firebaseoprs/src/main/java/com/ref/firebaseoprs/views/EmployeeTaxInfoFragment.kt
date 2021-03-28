package com.ref.firebaseoprs.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ref.baselibrary.dialogs.CustomAlertDialog
import com.ref.baselibrary.dialogs.DialogListener
import com.ref.baselibrary.dialogs.DialogModel
import com.ref.baselibrary.responsehelper.ResultOf
import com.ref.firebaseoprs.FirebaseoprsActivity
import com.ref.firebaseoprs.R
import com.ref.firebaseoprs.viewmodels.FireBaseViewModel
import kotlinx.android.synthetic.main.fragment_employee_tax_info.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmployeeTaxInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmployeeTaxInfoFragment : Fragment() ,DialogListener{
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
   /*  val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
               // showCustomAlertDialog(generateDialogModel(""))
                fireBaseViewModel.signOut()
              NavHostFragment.findNavController(this@EmployeeTaxInfoFragment).navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fireBaseViewModel =  (activity as FirebaseoprsActivity).fetchFireBaseViewModel()
        return inflater.inflate(R.layout.fragment_employee_tax_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      val  empEmail = arguments?.getString("email","")
        launch_tax_new.setOnClickListener {
findNavController().navigate(R.id.action_employeeTaxInfoFragment_to_employeeTaxEntryFragment,
    bundleOf("email" to empEmail))
        }

        observeSignout()

    }

    private fun observeSignout(){
        fireBaseViewModel.signOutStatus.observe(viewLifecycleOwner, Observer { result ->
            result?.let {
                when(it){

                    is ResultOf.Success ->{
                        if(it.value.equals("Signout Successful",ignoreCase = true)){
                            NavHostFragment.findNavController(this@EmployeeTaxInfoFragment).navigateUp()

                        }
                    }
                    is ResultOf.Failure -> {
                        val failedMessage =  it.message ?: "Unknown Error"
                        Toast.makeText(requireContext(),"Signout  failed $failedMessage", Toast.LENGTH_LONG).show()
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
         * @return A new instance of fragment EmployeeTaxInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmployeeTaxInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onPositiveButtonClick(actionType: String) {
        fireBaseViewModel.signOut()
    }

    override fun onNegativeButtonClick(actionType: String) {

    }
    private fun generateDialogModel(actionType: String) : DialogModel {
        return DialogModel(
            title = "Tax Info",
            content = "Are you sure you want to logout?",
            positiveButton = "OK",
            negativeButton = "Cancel",
            actionType = actionType,
            context = requireActivity(),
            activityContext = requireActivity()
        )
    }

    private fun showCustomAlertDialog(dialogModel: DialogModel){
        CustomAlertDialog.showAlertDialog(dialogModel,this)
    }
}