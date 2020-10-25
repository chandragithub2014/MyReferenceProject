package com.ref.bindingfeature.databindingrecyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ref.bindingfeature.R
import com.ref.bindingfeature.databinding.GitUserListItemBinding
import com.ref.bindingfeature.databindingrecyclerview.model.GitUserModel
import com.ref.bindingfeature.model.BindingModel

class GitUserAdapter(var context: Context, var gitUserList: GitUserModel) : RecyclerView.Adapter<GitUserAdapter.GitUserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitUserViewHolder {
       var gitUserListItemBinding = DataBindingUtil.inflate<GitUserListItemBinding>(
           LayoutInflater.from(parent.context),
           R.layout.git_user_list_item, parent, false)
        return  GitUserViewHolder(gitUserListItemBinding)
    }

    override fun getItemCount(): Int = gitUserList.size

    override fun onBindViewHolder(holder: GitUserViewHolder, position: Int)  = holder.bind(gitUserList[position])

    inner class GitUserViewHolder(private  val binding: GitUserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gitUser: GitUserModel.GitUserModelItem){
            binding.user = gitUser
            }


    }
}