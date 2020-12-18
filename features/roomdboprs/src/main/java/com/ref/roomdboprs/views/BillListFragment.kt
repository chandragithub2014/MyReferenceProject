package com.ref.roomdboprs.views

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ref.roomdboprs.viewmodels.UserViewModel
import com.ref.roomdboprs.viewmodels.UserViewModelFactory
import com.ref.roomdboprs.R
import com.ref.roomdboprs.adapters.UserBillAdapter
import com.ref.roomdboprs.interfaces.ItemClickListener
import com.ref.roomdboprs.model.User
import kotlinx.android.synthetic.main.fragment_bill_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BillListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BillListFragment : Fragment() , LifecycleOwner, ItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit  var billListView : View
    lateinit var userViewModel: UserViewModel

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
        billListView =  inflater.inflate(R.layout.fragment_bill_list, container, false)
        return billListView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userName  = arguments?.getString("userName","")
        println("UserName :: $userName")
        add_new_bill_fl_btn.setOnClickListener {
            if(!TextUtils.isEmpty(userName)) {
                findNavController().navigate(
                    R.id.action_billListFragment_to_addBillFragment,
                    bundleOf("userName" to userName)
                )
            }
        }
        if(!TextUtils.isEmpty(userName)){
            if (userName != null) {
                userViewModel.fetchUserBillDetails(userName).observe(viewLifecycleOwner, Observer {
                   it?.let {
                       populateBillAdapter(it)
                   }
                })

                userViewModel.fetchExpenseTotal(userName).observe(viewLifecycleOwner, Observer {
                    it?.let {
                        total_expenses.text = it.toString()
                    }
                })
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
         * @return A new instance of fragment BillListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BillListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


    private fun initViewModel(){
        var userViewModelFactory = UserViewModelFactory()
        userViewModel = ViewModelProvider(this,userViewModelFactory).get(UserViewModel::class.java)
        // placeHolderViewModel.fetchPostsFromService()


    }

    private fun populateBillAdapter(userBillList : List<User>){
        if(userBillList.isNotEmpty()){
            total_container.visibility = View.VISIBLE
        }

        val billsAdapter = UserBillAdapter(this@BillListFragment.requireActivity(),userBillList)
        billsAdapter.setInterface(this)
        val layoutManager = LinearLayoutManager(context)
        bill_rv.layoutManager = layoutManager
        bill_rv.adapter = billsAdapter
        bill_rv.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        bill_rv.scrollToPosition(userBillList.size - 1)
        billsAdapter.notifyDataSetChanged()

    }



    private fun launchEditFragment(user : User){
        findNavController().navigate(
            R.id.action_billListFragment_to_editBillFragment,
            bundleOf("UID" to user.uid)
        )
    }

    override fun onItemClick(user: User) {
        launchEditFragment(user)
    }
}