package com.ref.bindingfeature.databindingrecyclerview.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import colruyt.android.dsa.rayon.viewmodel.BindingViewModelFactory
import com.ref.baselibrary.navigator.replaceFragment
import com.ref.bindingfeature.R
import com.ref.bindingfeature.adapters.BindingListAdapter
import com.ref.bindingfeature.databinding.FragmentGitUserBinding
import com.ref.bindingfeature.databindingrecyclerview.adapter.GitUserAdapter
import com.ref.bindingfeature.databindingrecyclerview.interfaces.ItemClickListener
import com.ref.bindingfeature.databindingrecyclerview.model.GitUserModel
import com.ref.bindingfeature.databindingrecyclerview.viewmodel.GitUserViewModel
import com.ref.bindingfeature.databindingrecyclerview.viewmodel.GitUsersViewModelFactory
import com.ref.bindingfeature.model.BindingModel
import com.ref.bindingfeature.viewmodel.BindingViewModel
import kotlinx.android.synthetic.main.binding_list_layout.*
import kotlinx.android.synthetic.main.fragment_git_user.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GitUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GitUserFragment : Fragment(),ItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var gitUserFragmentBinding:FragmentGitUserBinding
    lateinit var  gitUserBindingView:View
    lateinit var gitUserViewModel:GitUserViewModel
    private var mContainer : Int = -1
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

        gitUserFragmentBinding =     DataBindingUtil.inflate(inflater,R.layout.fragment_git_user,container,false)
        gitUserFragmentBinding.lifecycleOwner = this
        gitUserBindingView = gitUserFragmentBinding.root
        mContainer = container?.id?:-1
        return gitUserBindingView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        observeViewModel()
        observeLoadStatus()
    }

    private fun initViewModel(){
        var gitUsersViewModelFactory = GitUsersViewModelFactory()
        gitUserViewModel = ViewModelProvider(this,gitUsersViewModelFactory).get(GitUserViewModel::class.java)
        gitUserViewModel.fetchUsersFromRepository()
    }

    private fun observeViewModel(){
        gitUserViewModel.fetchUsersLiveData().observe(viewLifecycleOwner, Observer {
           it?.let {
               populateAdapter(it)
           }
        })
    }

    private fun observeLoadStatus(){
        gitUserViewModel.fetchLoadStatus().observe(viewLifecycleOwner, Observer {
            if (!it) {
                println(it)
                progressBar.visibility = View.GONE
            }else{
                progressBar.visibility = View.VISIBLE
            }
        })
    }

    private fun populateAdapter(gitUserList : GitUserModel ){
        val gitUserListAdapter = GitUserAdapter(this@GitUserFragment.requireActivity(),gitUserList,this)
        val layoutManager = LinearLayoutManager(context)
        git_user_list.layoutManager = layoutManager
        git_user_list.adapter = gitUserListAdapter
        git_user_list.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        gitUserListAdapter.notifyDataSetChanged()
    }

    private fun launchDetailFragment( selectedUser:GitUserModel.GitUserModelItem){
        var gitUserDetailFragment = GitUserDetailFragment()
        var bundle = Bundle()
        bundle.putParcelable("gitUserInfo",selectedUser)
        gitUserDetailFragment.arguments = bundle
        activity?.replaceFragment(gitUserDetailFragment,mContainer)


    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GitUserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GitUserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun gitUserClicked(clickedUser: GitUserModel.GitUserModelItem) {
        launchDetailFragment(clickedUser)
    }
}