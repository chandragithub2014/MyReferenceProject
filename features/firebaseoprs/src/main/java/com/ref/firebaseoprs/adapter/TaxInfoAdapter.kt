package com.ref.firebaseoprs.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ref.firebaseoprs.R
import com.ref.firebaseoprs.interfaces.ClickListener
import com.ref.firebaseoprs.models.TaxInfo
import kotlinx.android.synthetic.main.emp_tax_item.view.*

class TaxInfoAdapter(var context: Context,
                     var taxInfoList : MutableList<TaxInfo>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var clickListener: ClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = TaxInfoViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.emp_tax_item, parent, false))

    override fun getItemCount(): Int = taxInfoList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
          var taxInfoItem = taxInfoList[position]
          holder as TaxInfoViewHolder
          holder.amountVal.text = taxInfoItem.taxDetailAmount
          holder.taxType.text = taxInfoItem.taxDetailType
          holder.uploadedDoc.tag = taxInfoItem.uploadUrl
          holder.uploadedDoc.setOnClickListener {
              val uploadDocURL =  holder.uploadedDoc.tag as String
              println("upLoadDocURL $uploadDocURL")
              clickListener.onPdfClicked(uploadDocURL)
          }


    }

    //Sets Data
    fun setData(taxInfoList:  MutableList<TaxInfo>){
        this.taxInfoList = taxInfoList

    }

    fun setInterface(clickListener: ClickListener){
        this.clickListener = clickListener
    }

    class TaxInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        internal var layout = itemView.tax_item_layout
        internal var taxType  = itemView.tax_type_label
        internal var uploadedDoc = itemView.uploaded_doc
        internal var amountVal =  itemView.amount_val


    }


}