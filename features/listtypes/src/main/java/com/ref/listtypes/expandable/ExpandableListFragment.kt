package com.ref.listtypes.expandable

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ref.baselibrary.responsehelper.ResultOf
import com.ref.listtypes.R
import com.ref.listtypes.expandable.adapter.CountryStateExpandableAdapter
import com.ref.listtypes.expandable.models.ExpandableCountryModel
import com.ref.listtypes.expandable.viewmodels.CountryStateViewModel
import com.ref.listtypes.expandable.viewmodels.CountryStateViewModelFactory
import com.ref.listtypes.viewmodels.ListTypeViewModel
import com.ref.listtypes.viewmodels.ListTypeViewModelFactory
import kotlinx.android.synthetic.main.fragment_expandable_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExpandableListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExpandableListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var countryStateViewModel: CountryStateViewModel
    lateinit var expandableView : View
    private var mContainer : Int = -1
    var countryStateExpandableAdapter : CountryStateExpandableAdapter? = null
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
        expandableView =  inflater.inflate(R.layout.fragment_expandable_list, container, false)
        return expandableView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countryStateViewModel.obtainCountryStateCapitals()
        observeViewModelResponse()
    }

    private fun observeViewModelResponse(){

        countryStateViewModel.obtainCountryStatesResponse.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it){

                    is ResultOf.Success -> {
                       val countryStateInfo =  countryStateViewModel.prepareDataForExpandableAdapter(it.value)
                        populateAdapterWithInfo(countryStateInfo)

                    }

                    is ResultOf.Failure -> {
                        val failedMessage =  it.message ?: "Unknown Error"
                        println("Failed Message $failedMessage")
                    }

                }
            }
        })
    }

    private fun populateAdapterWithInfo(expandableCountryStateList : MutableList<ExpandableCountryModel>){
        countryStateExpandableAdapter = CountryStateExpandableAdapter(this@ExpandableListFragment.requireActivity(),expandableCountryStateList)
        countryStateExpandableAdapter?.let {
            val layoutManager = LinearLayoutManager(context)
            country_state_rv.layoutManager = layoutManager
            country_state_rv.adapter = it
            country_state_rv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            it.notifyDataSetChanged()
        }

    }


    private fun initViewModel(){
        var countryStateViewModelFactory = CountryStateViewModelFactory()
        countryStateViewModel = ViewModelProvider(this,countryStateViewModelFactory).get(CountryStateViewModel::class.java)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExpandableListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExpandableListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}