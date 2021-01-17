package com.ref.hiltfeaturemodule.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ref.baselibrary.responsehelper.ResultOf
import com.ref.hiltfeature.adapter.PostsAdapter
import com.ref.hiltfeaturemodule.R
import com.ref.hiltfeaturemodule.model.PlaceHolderPostsDataModel
import com.ref.hiltfeaturemodule.viewmodel.HiltPostsViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_posts_hilt.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PostsHiltFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PostsHiltFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var postsView : View
    private var mContainer : Int = -1
    private val postViewModel by activityViewModels<HiltPostsViewModel>()
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
        postsView =  inflater.inflate(R.layout.fragment_posts_hilt, container, false)
        return postsView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postViewModel.fetchPosts()
        observePostsViewModel()
    }



    private fun observePostsViewModel(){
        postViewModel.loadingLiveData.observe(viewLifecycleOwner, Observer {
            if (!it) {
                println(it)
                load_progress.visibility = View.GONE
            }else{
                load_progress.visibility = View.VISIBLE
            }

        })
        postViewModel.postsResponse.observe(viewLifecycleOwner, Observer { result ->
            result?.let {
                println("ResultOf is $it")
                when(it){
                    is ResultOf.Success ->{
                        populatePostsAdapter(it.value)
                    }

                    is ResultOf.Failure -> {

                    }
                }
            }

        })
    }

    private fun populatePostsAdapter(postsList: PlaceHolderPostsDataModel){
        val postsAdapter = PostsAdapter(this@PostsHiltFragment.requireActivity(),postsList)
        val layoutManager = LinearLayoutManager(context)
        hilt_posts_rv.layoutManager = layoutManager
        hilt_posts_rv.adapter = postsAdapter
        hilt_posts_rv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        postsAdapter.notifyDataSetChanged()

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PostsHiltFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostsHiltFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}