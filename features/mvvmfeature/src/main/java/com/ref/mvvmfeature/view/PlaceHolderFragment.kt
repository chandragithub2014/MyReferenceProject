package com.ref.mvvmfeature.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import colruyt.android.dsa.rayon.viewmodel.PlaceHolderViewModel
import colruyt.android.dsa.rayon.viewmodel.PlaceHolderViewModelFactory
import com.ref.mvvmfeature.R
import com.ref.mvvmfeature.adapters.CommentsAdapter
import com.ref.mvvmfeature.adapters.PostsAdapter
import com.ref.mvvmfeature.model.PlaceHolderCommentsModel
import com.ref.mvvmfeature.model.PlaceHolderPostsDataModel
import kotlinx.android.synthetic.main.fragment_place_holder.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlaceHolderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlaceHolderFragment : Fragment(), LifecycleOwner {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var placeHolderViewModel: PlaceHolderViewModel
    lateinit var placeHolderFragmentView:View
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
        placeHolderFragmentView =  inflater.inflate(R.layout.fragment_place_holder, container, false)
        return  placeHolderFragmentView
     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         observePostsResponse()
         observeCommentsResponse()
         observeLoadStatus()
    }

    private fun initViewModel(){
        var placeHolderViewModelFactory = PlaceHolderViewModelFactory()
        placeHolderViewModel = ViewModelProvider(this,placeHolderViewModelFactory).get(PlaceHolderViewModel::class.java)
       // placeHolderViewModel.fetchPostsFromService()
        placeHolderViewModel.fetchPostsAndComments()

    }

    private fun observePostsResponse(){
        placeHolderViewModel.fetchPostsLiveData().observe(viewLifecycleOwner, Observer {
          //  val postsList = it
            populatePostsAdapter(it)

           // placeHolderViewModel.fetchCommentsFromService()

        })
    }

    private fun observeCommentsResponse(){
        placeHolderViewModel.fetchCommentsLiveData().observe(viewLifecycleOwner, Observer {
                        populateCommentsAdapter(it)
        })
    }

    private fun observeLoadStatus(){
        placeHolderViewModel.fetchLoadStatus().observe(viewLifecycleOwner, Observer {
            if (!it) {
                println(it)
                loading_widget.visibility = View.GONE
            }else{
                loading_widget.visibility = View.VISIBLE
            }
        })
    }

    private fun populatePostsAdapter(postsList:PlaceHolderPostsDataModel){
       val postsAdapter = PostsAdapter(this@PlaceHolderFragment.requireActivity(),postsList)
        val layoutManager = LinearLayoutManager(context)
        posts_rv.layoutManager = layoutManager
        posts_rv.adapter = postsAdapter
        posts_rv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        postsAdapter.notifyDataSetChanged()

    }


    private fun populateCommentsAdapter(commentsList:PlaceHolderCommentsModel){
       val commentsAdapter = CommentsAdapter(this@PlaceHolderFragment.requireActivity(),commentsList)
        val layoutManager = LinearLayoutManager(context)
        comments_rv.layoutManager = layoutManager
        comments_rv.adapter = commentsAdapter
        comments_rv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        commentsAdapter.notifyDataSetChanged()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PlaceHolderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PlaceHolderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}