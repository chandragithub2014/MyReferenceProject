package com.ref.listtypes.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ref.listtypes.R
import com.ref.listtypes.interfaces.ListTypeListener
import com.ref.listtypes.models.ListTypeModel
import kotlinx.android.synthetic.main.list_type_list_item.view.*

class ListTypeAdapter(var context: Context, var listTypeList : MutableList<ListTypeModel>, var listTypeListener: ListTypeListener) : RecyclerView.Adapter<ListTypeAdapter.ListTypeViewHolder>(){



    inner class ListTypeViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val parent = view.list_type_item_parent
        private val bindingItem = view.list_type_list_item

        fun bind(bindingModel: ListTypeModel){
            bindingItem.text = bindingModel.listType
            parent.setOnClickListener {
                println("Clicked on ${bindingItem.text}")
                listTypeListener.onListTypeClick(bindingItem.text as String)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTypeViewHolder =  ListTypeViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.list_type_list_item, parent, false))

    override fun getItemCount(): Int  = listTypeList.size

    override fun onBindViewHolder(holder: ListTypeViewHolder, position: Int)  =  holder.bind(listTypeList[position])



}