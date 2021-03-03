package com.ref.listtypes.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ref.baselibrary.navigator.openActivity
import com.ref.baselibrary.responsehelper.ResultOf
import com.ref.listtypes.R
import com.ref.listtypes.adapters.ListTypeAdapter
import com.ref.listtypes.interfaces.ListTypeListener
import com.ref.listtypes.models.ListTypeModel
import com.ref.listtypes.viewmodels.ListTypeViewModel
import com.ref.listtypes.viewmodels.ListTypeViewModelFactory
import kotlinx.android.synthetic.main.fragment_list_types.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListTypesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListTypesFragment : Fragment() , ListTypeListener{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var listTypeViewModel: ListTypeViewModel
    lateinit var listTypeView : View
    private var mContainer : Int = -1

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
        listTypeView =  inflater.inflate(R.layout.fragment_list_types, container, false)
        mContainer = container?.id?:-1
        return  listTypeView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listTypeViewModel.obtainListTypeInfo()
        observeViewModel()

    }

    private fun observeViewModel(){
        listTypeViewModel.obtainListTypeResponse.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it){
                  is ResultOf.Success -> {
                      populateAdapter(it.value)
                  }
                  is ResultOf.Failure -> {
                      val failedMessage =  it.message ?: "Unknown Error"
                      println("Failed Message $failedMessage")
                  }
                }
            }
        })
    }
    private fun populateAdapter(listTypeList : MutableList<ListTypeModel>){
        val listTypeAdapter = ListTypeAdapter(this@ListTypesFragment.requireActivity(),listTypeList,this)
        val layoutManager = LinearLayoutManager(context)

        listtypes_rv.layoutManager = layoutManager
        listtypes_rv.adapter = listTypeAdapter
        listtypes_rv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        listTypeAdapter.notifyDataSetChanged()
    }

    private fun initViewModel(){
        var listTypeViewModelFactory = ListTypeViewModelFactory()
        listTypeViewModel = ViewModelProvider(this,listTypeViewModelFactory).get(ListTypeViewModel::class.java)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListTypesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListTypesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onListTypeClick(listType: String) {
        when(listType){
            "ExpandableRecyclerView" -> findNavController().navigate(R.id.action_listTypesFragment_to_expandableListFragment)
            "SectionedRecyclerView" ->  findNavController().navigate(R.id.action_listTypesFragment_to_sectionedListFragment)
            "InfiniteList" -> findNavController().navigate(R.id.action_listTypesFragment_to_infiniteListFragment2)
            else -> findNavController().navigate(R.id.action_listTypesFragment_to_expandableListFragment)
        }
    }
}