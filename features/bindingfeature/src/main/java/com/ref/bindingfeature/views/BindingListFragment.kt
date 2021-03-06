package com.ref.bindingfeature.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import colruyt.android.dsa.rayon.viewmodel.BindingViewModelFactory
import com.ref.baselibrary.navigator.openActivity
import com.ref.baselibrary.navigator.replaceFragment
import com.ref.bindingfeature.R
import com.ref.bindingfeature.adapters.BindingListAdapter
import com.ref.bindingfeature.databindinglistdetailnetwork.view.GitUserFragment
import com.ref.bindingfeature.interfaces.BindingListener
import com.ref.bindingfeature.model.BindingModel
import com.ref.bindingfeature.viewbinding.views.LoginViewBindingActivity
import com.ref.bindingfeature.viewmodel.BindingViewModel
import kotlinx.android.synthetic.main.binding_list_layout.*

class BindingListFragment : Fragment() ,BindingListener{


    lateinit var bindingViewModel: BindingViewModel
    lateinit var bindingListView : View
    private var mContainer : Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingListView =  inflater.inflate(R.layout.binding_list_layout, container, false)
        mContainer = container?.id?:-1
        return  bindingListView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingViewModel.fetchBindingListFromUtils()
        observeViewModel()
    }
    private fun initViewModel(){
        var bindingViewModelFactory = BindingViewModelFactory()
        bindingViewModel = ViewModelProvider(this,bindingViewModelFactory).get(BindingViewModel::class.java)
    }


    private fun observeViewModel(){
        bindingViewModel.fetchBindingListLiveData().observe(viewLifecycleOwner, Observer {
            it?.let{
                populateAdapter(it)
            }
        })
    }


    private fun populateAdapter(bindingList : MutableList<BindingModel>){
        val bindingListAdapter = BindingListAdapter(this@BindingListFragment.requireActivity(),bindingList,this)
        val layoutManager = LinearLayoutManager(context)

        binding_rv.layoutManager = layoutManager
        binding_rv.adapter = bindingListAdapter
        binding_rv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        bindingListAdapter.notifyDataSetChanged()
    }

    override fun onBindTemplateClick(template: String) {
        when(template){
            "ViewBinding" -> activity?.openActivity(LoginViewBindingActivity::class.java)
            "DataBindingList/Detail+Network" -> launchGitUserDataBindingFragment()
                else -> activity?.openActivity(LoginViewBindingActivity::class.java)
        }
    }


    private fun launchGitUserDataBindingFragment(){

        activity?.replaceFragment(GitUserFragment(),mContainer)
    }
}