package com.ref.listtypes.sectioned.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ref.listtypes.R
import com.ref.listtypes.expandable.models.ExpandableCountryModel
import com.ref.listtypes.expandable.models.StateCapital
import com.ref.listtypes.sectioned.interfaces.CountryClickedListener
import kotlinx.android.synthetic.main.expandable_child_item.view.*
import kotlinx.android.synthetic.main.expandable_parent_item.view.*

class CountryStateSectionedAdapter(var countryClickedListener: CountryClickedListener, var countryStateModelList:MutableList<ExpandableCountryModel>) :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var isFirstItemExpanded : Boolean = true
    private var actionLock = false
    lateinit var countryName:String
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       return when(viewType) {
           ExpandableCountryModel.PARENT -> {CountryStateParentViewHolder(LayoutInflater.from(parent.context).inflate(
               R.layout.expandable_parent_item, parent, false))}

            ExpandableCountryModel.CHILD -> { CountryStateChildViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.expandable_child_item, parent, false))  }

           else -> {CountryStateParentViewHolder(LayoutInflater.from(parent.context).inflate(
               R.layout.expandable_parent_item, parent, false))}
       }
    }

    override fun getItemCount(): Int = countryStateModelList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
       val row = countryStateModelList[position]
        when(row.type){
            ExpandableCountryModel.PARENT -> {
                (holder as CountryStateParentViewHolder).countryName.text = row.countryParent.country
                countryName = row.countryParent.country
                holder.closeImage.visibility = View.GONE
                holder.upArrowImg.visibility = View.GONE

            }


            ExpandableCountryModel.CHILD -> {
                (holder as CountryStateChildViewHolder).stateName.text = row.countryChild.name
                holder.capitalImage.text = row.countryChild.capital
                countryName?.let {
                    holder.layout.tag = it
                }
                holder.stateName.tag = row.countryChild
                holder.layout.setOnClickListener {
                    var countryInfo =   holder.stateName.tag
                    countryClickedListener.onItemClick(holder.layout.tag.toString(),
                        countryInfo as StateCapital.Country.State
                    )
                }
            }
        }

    }


    override fun getItemViewType(position: Int): Int = countryStateModelList[position].type




    class CountryStateParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var layout = itemView.country_item_parent_container
        internal var countryName : TextView = itemView.country_name
        internal var closeImage = itemView.close_arrow
        internal var upArrowImg = itemView.up_arrow

    }

    class CountryStateChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var layout = itemView.country_item_child_container
        internal var stateName : TextView = itemView.state_name
        internal var capitalImage = itemView.capital_name

    }
}