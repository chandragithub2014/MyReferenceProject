package com.ref.mvvmfeature.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ref.mvvmfeature.R
import com.ref.mvvmfeature.model.PlaceHolderCommentsModel
import kotlinx.android.synthetic.main.comments_item.view.*


class CommentsAdapter (var context: Context, var commentList : PlaceHolderCommentsModel) : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder =  CommentViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.comments_item, parent, false))

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) = holder.bind(commentList[position])

    override fun getItemCount(): Int = commentList.size

    inner class CommentViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val id = view.id_val
        private val title = view.title_val
        private val email = view.email_val
        private val comments = view.comments_val

        fun bind(commentItemModel : PlaceHolderCommentsModel.PlaceHolderCommentsModelItem){
            id.text = commentItemModel.id.toString()
            title.text = commentItemModel.name
            email.text = commentItemModel.email
            comments.text = commentItemModel.body
        }

    }
}