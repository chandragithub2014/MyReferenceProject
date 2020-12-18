package com.ref.bindingfeature.databindinglistdetailnetwork.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.ref.bindingfeature.R
import com.ref.bindingfeature.databinding.FragmentGitUserDetailBinding
import com.ref.bindingfeature.databindinglistdetailnetwork.model.GitUserModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GitUserDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GitUserDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var gitUserDetailView:View
    lateinit var fragmentGitUserDetailBinding: FragmentGitUserDetailBinding
    var selectedUser: GitUserModel.GitUserModelItem?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val bundle = this.arguments
        if (bundle != null) {
            selectedUser = bundle.getParcelable("gitUserInfo") // Key
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentGitUserDetailBinding =     DataBindingUtil.inflate(inflater,R.layout.fragment_git_user_detail,container,false)
        fragmentGitUserDetailBinding.lifecycleOwner = this
        fragmentGitUserDetailBinding.gituser = selectedUser
        gitUserDetailView = fragmentGitUserDetailBinding.root
        return gitUserDetailView

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GitUserDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GitUserDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}