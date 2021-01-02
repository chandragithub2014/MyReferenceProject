package com.ref.listtypes.sectioned

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ref.baselibrary.responsehelper.ResultOf
import com.ref.listtypes.R
import com.ref.listtypes.expandable.adapter.CountryStateExpandableAdapter
import com.ref.listtypes.expandable.models.ExpandableCountryModel
import com.ref.listtypes.expandable.models.StateCapital
import com.ref.listtypes.expandable.viewmodels.CountryStateViewModel
import com.ref.listtypes.expandable.viewmodels.CountryStateViewModelFactory
import com.ref.listtypes.sectioned.adapters.CountryStateSectionedAdapter
import com.ref.listtypes.sectioned.interfaces.CountryClickedListener
import kotlinx.android.synthetic.main.fragment_expandable_list.*
import kotlinx.android.synthetic.main.fragment_sectioned_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SectionedListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SectionedListFragment : Fragment(),CountryClickedListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var countryStateViewModel: CountryStateViewModel
    lateinit var sectionedView : View
    private var mContainer : Int = -1
    var countryStateSectionedAdapter : CountryStateSectionedAdapter? = null
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
        sectionedView =  inflater.inflate(R.layout.fragment_sectioned_list, container, false)
        return sectionedView
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
                        val countryStateInfo =  countryStateViewModel.prepareDataForSectionedAdapter(it.value)
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
        countryStateSectionedAdapter = CountryStateSectionedAdapter(this,expandableCountryStateList)
        countryStateSectionedAdapter?.let {
            val layoutManager = LinearLayoutManager(context)
            country_state_sectioned_list_rv.layoutManager = layoutManager
            country_state_sectioned_list_rv.adapter = it
            country_state_sectioned_list_rv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
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
         * @return A new instance of fragment SectionedListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SectionedListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(countryName: String, countryChild: StateCapital.Country.State) {
        Toast.makeText(this@SectionedListFragment.requireActivity(),"Clicked on $countryName with info ${countryChild.name} and ${countryChild.capital}",Toast.LENGTH_LONG).show()
    }
}