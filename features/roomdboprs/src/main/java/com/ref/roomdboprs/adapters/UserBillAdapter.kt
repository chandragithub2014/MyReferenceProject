package com.ref.roomdboprs.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ref.roomdboprs.R
import com.ref.roomdboprs.interfaces.ItemClickListener
import com.ref.roomdboprs.model.User
import kotlinx.android.synthetic.main.bill_item.view.*


class UserBillAdapter(var context: Context, var userBillList : List<User>) : RecyclerView.Adapter<UserBillAdapter.BillViewHolder>() {
    lateinit var itemClickListener: ItemClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillViewHolder =  BillViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.bill_item, parent, false))

    override fun onBindViewHolder(holder: BillViewHolder, position: Int)  {
        holder.bind(userBillList[position])
        holder.itemContainer.tag = userBillList[position]
        holder.itemContainer.setOnClickListener {
            itemClickListener.onItemClick(holder.itemContainer.tag as User)
        }
    }

    override fun getItemCount(): Int = userBillList.size

    fun setInterface(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

     class BillViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var userId = view.userName_val
        private var place = view.place_val
        private var purpose = view.purpose_val
        private var expense = view.expense_val
        private var dateVisted = view.date_val
        internal var itemContainer = view.bill_item_container

        fun bind(user : User){
            userId.text = user.userName
            place.text = user.placeVisited
            purpose.text = user.visitPurpose
            expense.text = "Rs."+user.visitExpense.toString()
            dateVisted.text = user.visitedDate
        }

    }


}