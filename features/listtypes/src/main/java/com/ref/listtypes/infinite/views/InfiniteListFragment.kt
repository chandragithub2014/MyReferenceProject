package com.ref.listtypes.infinite.views

import android.opengl.Visibility
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
import com.ref.listtypes.expandable.viewmodels.CountryStateViewModel
import com.ref.listtypes.expandable.viewmodels.CountryStateViewModelFactory
import com.ref.listtypes.infinite.adapters.InfiniteAdapter
import com.ref.listtypes.infinite.interfaces.LoadMore
import com.ref.listtypes.infinite.model.InfiniteAdapterModel
import com.ref.listtypes.infinite.model.InfiniteModel
import com.ref.listtypes.infinite.viewmodels.InfiniteListViewModel
import com.ref.listtypes.infinite.viewmodels.InfiniteViewModelFactory
import kotlinx.android.synthetic.main.fragment_expandable_list.*
import kotlinx.android.synthetic.main.fragment_infinite_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InfiniteListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfiniteListFragment : Fragment() ,LoadMore{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var infiniteListView : View
    private var mContainer : Int = -1
    lateinit var infiniteListViewModel: InfiniteListViewModel
    private var infiniteModel:InfiniteModel? = null
    private var position:Int = 0
    var infiniteAdapter:InfiniteAdapter? = null
    private var startPosition:Number = 0
    private var endPosition:Number = 0
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
        infiniteListView =  inflater.inflate(R.layout.fragment_infinite_list, container, false)

        return infiniteListView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        infiniteListViewModel.obtainInfiniteList()
        observeViewModel()
    }

    private fun initViewModel(){
        var infiniteViewModelFactory = InfiniteViewModelFactory()
        infiniteListViewModel = ViewModelProvider(this,infiniteViewModelFactory).get(
            InfiniteListViewModel::class.java)
    }

    private fun observeViewModel(){
        infiniteListViewModel.obtainInfiniteListResponse.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it){
                    is ResultOf.Success -> {
                        infiniteModel = it.value
                       infiniteModel?.let { model ->
                           if(model.size >=10){
                               startPosition = 0
                               endPosition = 10
                               val preparedData = infiniteListViewModel.prepareDataForAdapter(model,startPosition.toInt(),
                                   endPosition.toInt())

                               populateAdapter(preparedData,startPosition,endPosition,model)
                           }

                       }

                    }

                    is ResultOf.Failure -> {
                        val failedMessage =  it.message ?: "Unknown Error"
                        println("Failed Message $failedMessage")
                    }
                }
            }
        })
    }

    private fun populateAdapter(infiniteAdapterModelList : MutableList<InfiniteAdapterModel>,startPosition:Number,endPosition:Number,originalModel:InfiniteModel){

        infiniteAdapter?.let {
            val layoutManager = LinearLayoutManager(context)
              infinite_rv.layoutManager = layoutManager
             infinite_rv.adapter = it
            infinite_rv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            it.setFiniteList(startPosition,endPosition,infiniteAdapterModelList,originalModel,this)
            it.notifyDataSetChanged()
            progressBar.visibility = View.GONE
        }
    }
    private fun initAdapter(){
        infiniteAdapter = InfiniteAdapter(this@InfiniteListFragment.requireActivity(), mutableListOf())
        infiniteAdapter?.let {
            val layoutManager = LinearLayoutManager(context)
            infinite_rv.layoutManager = layoutManager
            infinite_rv.adapter = it
            infinite_rv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            it.notifyDataSetChanged()
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InfiniteListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InfiniteListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun loadMore(
        startPosition: Int,
        endPosition: Int,
        previousList: MutableList<InfiniteAdapterModel>,
        originalModel: InfiniteModel
    ) {
        previousList.removeAt(previousList.size -1)
        val scrollPosition = previousList.size
        infiniteAdapter?.let {
            it.notifyItemRemoved(scrollPosition)
            var currentSize = scrollPosition
            var nextLimit = currentSize+10
            if(nextLimit<=originalModel.size) {
                val preparedData = infiniteListViewModel.prepareDataForAdapter(
                    originalModel, currentSize,
                    nextLimit
                          //  it.setFiniteList(startPosition,endPosition,infiniteAdapterModelList,originalModel,this)
                )
                previousList.addAll(preparedData)
                it.setFiniteList(currentSize,nextLimit,previousList,originalModel,this)
                it.notifyDataSetChanged()
            }else if(currentSize<originalModel.size){
                nextLimit =  originalModel.size
                val preparedData = infiniteListViewModel.prepareDataForAdapter(
                    originalModel, currentSize,
                    nextLimit
                    //  it.setFiniteList(startPosition,endPosition,infiniteAdapterModelList,originalModel,this)
                )
                previousList.addAll(preparedData)
                currentSize = originalModel.size
                it.setFiniteList(currentSize,nextLimit,previousList,originalModel,this)
                it.notifyDataSetChanged()
            }

        }
    }
}