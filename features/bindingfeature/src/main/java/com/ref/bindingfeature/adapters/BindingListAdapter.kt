package com.ref.bindingfeature.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ref.bindingfeature.R
import com.ref.bindingfeature.model.BindingModel
import kotlinx.android.synthetic.main.binding_list_item.view.*

class BindingListAdapter(var context: Context, var bindingList : MutableList<BindingModel>) : RecyclerView.Adapter<BindingListAdapter.BindingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingListAdapter.BindingViewHolder =  BindingViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.binding_list_item, parent, false))

    override fun getItemCount(): Int = bindingList.size


    override fun onBindViewHolder(holder: BindingViewHolder, position: Int)  =  holder.bind(bindingList[position])



   inner class BindingViewHolder(view: View) : RecyclerView.ViewHolder(view){
       private val parent = view.binding_item_parent
       private val bindingItem = view.binding_list_item

       fun bind(bindingModel: BindingModel){
           bindingItem.text = bindingModel.bindingType
           parent.setOnClickListener {
               println("Clicked on ${bindingItem.text}")

           }
       }
   }


}