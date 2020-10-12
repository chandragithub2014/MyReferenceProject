package com.ref.mvvmfeature.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ref.mvvmfeature.R
import com.ref.mvvmfeature.model.PlaceHolderPostsDataModel
import kotlinx.android.synthetic.main.posts_item.view.*

class PostsAdapter(var context: Context,var postList : PlaceHolderPostsDataModel) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =  PostViewHolder(
        LayoutInflater.from(parent.context).inflate(
    R.layout.posts_item, parent, false))

    override fun onBindViewHolder(holder: PostViewHolder, position: Int)  =  holder.bind(postList[position])

    override fun getItemCount(): Int = postList.size



    inner class PostViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val id = view.id_val
        private val title = view.title_val
        private val post = view.post_val

        fun bind(postItemModel : PlaceHolderPostsDataModel.PlaceHolderPostsModelItem){
            id.text = postItemModel.id.toString()
            title.text = postItemModel.title
            post.text = postItemModel.body
        }

    }


}