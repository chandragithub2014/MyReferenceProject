package com.ref.bindingfeature.databindinglistdetailnetwork.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ref.bindingfeature.R
import com.ref.bindingfeature.databinding.GitUserListItemBinding
import com.ref.bindingfeature.databindinglistdetailnetwork.interfaces.ItemClickListener
import com.ref.bindingfeature.databindinglistdetailnetwork.model.GitUserModel


class GitUserAdapter(var context: Context, var gitUserList: GitUserModel,var itemClickListener: ItemClickListener) : RecyclerView.Adapter<GitUserAdapter.GitUserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitUserViewHolder {
       var gitUserListItemBinding : GitUserListItemBinding = DataBindingUtil.inflate(
           LayoutInflater.from(parent.context),
           R.layout.git_user_list_item, parent, false)

        return  GitUserViewHolder(gitUserListItemBinding)
    }

    override fun getItemCount(): Int = gitUserList.size

    override fun onBindViewHolder(holder: GitUserViewHolder, position: Int)  {
        holder.bind(gitUserList[position])

    }

    inner class GitUserViewHolder(private  val binding: GitUserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(gitUser: GitUserModel.GitUserModelItem){
            binding.gituser = gitUser
            binding.gitUserItemLayout.tag = gitUser
            binding.gitUserItemLayout.setOnClickListener {
                val clickedGitUser:GitUserModel.GitUserModelItem = binding.gitUserItemLayout.tag as GitUserModel.GitUserModelItem
                itemClickListener.gitUserClicked(clickedGitUser)
               println("Clicked Item is $clickedGitUser")
            }
            }


    }
}
