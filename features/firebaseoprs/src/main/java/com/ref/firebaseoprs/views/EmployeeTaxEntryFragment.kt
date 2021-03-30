package com.ref.firebaseoprs.views

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ref.baselibrary.responsehelper.ResultOf
import com.ref.firebaseoprs.FirebaseoprsActivity
import com.ref.firebaseoprs.R
import com.ref.firebaseoprs.adapter.CustomDropDownForTaxDetailTypesAdapter
import com.ref.firebaseoprs.models.TaxInfo
import com.ref.firebaseoprs.viewmodels.FireBaseViewModel
import kotlinx.android.synthetic.main.fragment_employee_tax_entry.*
import kotlinx.android.synthetic.main.fragment_login.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmployeeTaxEntryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class EmployeeTaxEntryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var selectedImageUri: Uri? = null
    private lateinit var  fireBaseViewModel: FireBaseViewModel
    private var selectedTaxType:String = ""
    private var uploadedURL:String = ""
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
        fireBaseViewModel =  (activity as FirebaseoprsActivity).fetchFireBaseViewModel()
        return inflater.inflate(R.layout.fragment_employee_tax_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val  empEmail = arguments?.getString("email","")
        println("Received Email $empEmail")
        observerLoadingProgress()
        observeResponseFromUploader()
        email_label.text = empEmail
        val taxDetailTypes=      resources.getStringArray(R.array.tax_details_array).toMutableList()
        populateDataForTaxDetailType(taxDetailTypes)
        upload_doc_img_btn.setOnClickListener {
            openPDFChooser(REQUEST_PICK_IMAGE)
        }

        save_btn.setOnClickListener {
             if(TextUtils.isEmpty(amount_info_ed.text.toString()) || TextUtils.isEmpty(uploadedURL) || TextUtils.isEmpty(selectedTaxType)){
                 Toast.makeText(requireContext(),"Please Provide proper details for all fields",Toast.LENGTH_LONG).show()
             }else {
               val taxInfo = TaxInfo(email_label.text.toString(),selectedTaxType,amount_info_ed.text.toString(),uploadedURL)
                 fireBaseViewModel.saveTaxDetails(taxInfo)
                 observeDataBaseResponse()
             }
        }

        cancel_btn.setOnClickListener {
            findNavController().popBackStack()
        }

    }


    private fun openPDFChooser(requestCode : Int) {

        val intentPDF = Intent(Intent.ACTION_GET_CONTENT)
        intentPDF.type = "application/pdf"
        intentPDF.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(Intent.createChooser(intentPDF, "Select Picture"), requestCode)
    }

    private fun observeDataBaseResponse(){
        fireBaseViewModel.saveResult.observe(viewLifecycleOwner, Observer { result ->
            result?.let {
                when(it){
                    is ResultOf.Success ->{
                        if(it.value.equals("Data Saved Successfully",ignoreCase = true)){
                            Toast.makeText(requireContext(),"Tax Details Saved",Toast.LENGTH_LONG).show()

                        }else{
                            println("Data failed to save")

                        }
                    }
                    is ResultOf.Failure -> {
                        val failedMessage =  it.message ?: "Unknown Error"
                        Toast.makeText(requireContext(),"Save failed $failedMessage", Toast.LENGTH_LONG).show()
                    }
                }
            }

        })
    }
    private fun observeResponseFromUploader(){
        fireBaseViewModel.uploadDocResult.observe(viewLifecycleOwner, Observer { result ->
            result?.let {
                when(it){
                    is ResultOf.Success ->{
                        if(it.value.equals("Upload Failed",ignoreCase = true)){
                            Toast.makeText(requireContext(),"Upload failed",Toast.LENGTH_LONG).show()

                        }else{
                            println("Received upload URL is ${it.value}")
                            uploadedURL = it.value
                            findNavController().popBackStack()
                        }
                    }
                    is ResultOf.Failure -> {
                        val failedMessage =  it.message ?: "Unknown Error"
                        Toast.makeText(requireContext(),"Upload failed $failedMessage", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
     //   super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
              REQUEST_PICK_IMAGE -> {
                  selectedImageUri = data?.data
                  // imageView.setImageURI(selectedImageUri)
                  selectedImageUri?.let { imageUri ->
                      fireBaseViewModel.uploadDoc(imageUri)
                  }

              }
            }
        }
    }
    private fun populateDataForTaxDetailType( detailTypeList:MutableList<String>){
        val customDropDownAdapter = CustomDropDownForTaxDetailTypesAdapter(this.requireContext(), R.layout.custom_spinner_tax_detail_type_item,detailTypeList,spinner =  tax_detail_picker)
        tax_detail_picker.adapter = customDropDownAdapter
        tax_detail_picker.setSelection(Adapter.NO_SELECTION, true)
        selectedTaxType = detailTypeList[0]
        tax_detail_picker.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val clickedItem  = parent!!.getItemAtPosition(position) as String
                val taxDetailType: String = clickedItem
                selectedTaxType = clickedItem
                println("selected FilterName $taxDetailType")
                //   close_less_super_img_view_executed_today.text = resources.getString(colruyt.android.dsa.ui_components.R.string.less_super_confirm_label)
                //  close_less_super_img_view_executed_today.paintFlags =  close_less_super_img_view_executed_today.paintFlags or Paint.UNDERLINE_TEXT_FLAG
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
         * @return A new instance of fragment EmployeeTaxEntryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmployeeTaxEntryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }




            private val REQUEST_PICK_IMAGE = 2

            private val TAG = "EmployeeTaxEntryFragment"



    }

    private fun observerLoadingProgress(){
        fireBaseViewModel.fetchLoading().observe(viewLifecycleOwner, Observer {
            if (!it) {
                println(it)
                progressBar_tax_entry.visibility = View.GONE
            }else{
                progressBar_tax_entry.visibility = View.VISIBLE
            }

        })


    }
}