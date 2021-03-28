package com.ref.firebaseoprs.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.content.ContextCompat

class CustomDropDownForTaxDetailTypesAdapter(context: Context, textViewResourceId: Int, var dataSource: MutableList<String>, var selectedPositon:Int = 0, var spinner: AppCompatSpinner) : ArrayAdapter<String>(
    context,
    textViewResourceId,
    dataSource

)
{
    override fun getCount() = dataSource.size

    override fun getItem(position: Int) = dataSource[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return (super.getDropDownView(position, convertView, parent) as TextView).apply {
            text = dataSource[position]


        }
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return (super.getDropDownView(position, convertView, parent) as TextView).apply {
            text = dataSource[position]
            if(position == spinner.selectedItemPosition){
                setBackgroundColor(ContextCompat.getColor(context,com.ref.baseuilibrary.R.color.cg_notification_information))
                setTextColor(Color.WHITE)
            }
            /*setOnClickListener {
               println("Selected Item is ${dataSource[position].filterDescription}")
            }*/

        }
    }
}