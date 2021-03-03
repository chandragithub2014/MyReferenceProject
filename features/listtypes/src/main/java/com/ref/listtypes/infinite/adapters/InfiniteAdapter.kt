package com.ref.listtypes.infinite.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ref.listtypes.R
import com.ref.listtypes.infinite.interfaces.LoadMore
import com.ref.listtypes.infinite.model.InfiniteAdapterModel
import com.ref.listtypes.infinite.model.InfiniteModel
import kotlinx.android.synthetic.main.infinite_list_item.view.*
import kotlinx.android.synthetic.main.infinite_loading.view.*
import java.text.FieldPosition

class InfiniteAdapter(var context: Context, private var infiniteList: MutableList<InfiniteAdapterModel>) :  RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object
    {
        private const val VIEW_TYPE_DATA = 0;
        private const val VIEW_TYPE_PROGRESS = 1;
    }
   // private var infiniteList : MutableList<InfiniteAdapterModel> = mutableListOf()
    private var originalModel : InfiniteModel?=null
    private var startPosition:Number = 0
    private var endPosition:Number = 0
    lateinit var loadMore: LoadMore
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType)
        {
            VIEW_TYPE_DATA ->
            {//inflates row layout
                val view = LayoutInflater.from(parent.context).inflate(R.layout.infinite_list_item,parent,false)
                InfiniteListViewHolder(view)
            }
            VIEW_TYPE_PROGRESS ->
            {//inflates progressbar layout
                val view = LayoutInflater.from(parent.context).inflate(R.layout.infinite_loading,parent,false)
                InfiniteLoadingViewHolder(view)
            }
            else -> throw IllegalArgumentException("Different View type")
        }
    }

    override fun getItemCount(): Int  = infiniteList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val row = infiniteList[position]
        when(row.type){
            VIEW_TYPE_DATA ->{
                if (holder is InfiniteListViewHolder)
                {
                    holder.bind(infiniteList[position].infiniteModelItem)
                }
            }
            VIEW_TYPE_PROGRESS -> {
                if (holder is InfiniteLoadingViewHolder){
                    holder.loadingItem.setOnClickListener {
                        originalModel?.let { it1 ->
                            loadMore.loadMore(
                                startPosition as Int,
                                endPosition as Int,infiniteList, it1
                            )
                        }
                    }
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        var viewtype = infiniteList[position].type
        return when(viewtype)
        {
            1  -> VIEW_TYPE_PROGRESS
            else -> VIEW_TYPE_DATA
        }
    }
    /*
      private var infiniteList : MutableList<InfiniteAdapterModel> = mutableListOf()
    private var orignalInfiniteList : MutableList<InfiniteAdapterModel> = mutableListOf()
    private var startPostion:Number = 0
    private var endPosition:Number = 0
     */
    fun setFiniteList(startPosition:Number
                      ,endPosition: Number
                      ,infiniteList: MutableList<InfiniteAdapterModel>
                      ,originalModel : InfiniteModel
                      ,loadMore: LoadMore){
     this.infiniteList = infiniteList
     this.originalModel = originalModel
     this.startPosition = startPosition
     this.endPosition = endPosition
     this.loadMore = loadMore
     notifyDataSetChanged()

    }

    inner class InfiniteListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val parent = view.infinite_item_child_container
        private val  title = view.title_item
        private val  name = view.id_name

        fun bind(infiniteModelItem: InfiniteModel.InfiniteModelItem){
          title.text = infiniteModelItem.title
          name.text = infiniteModelItem.id.toString()
        }
    }

   inner class InfiniteLoadingViewHolder(view:View) : RecyclerView.ViewHolder(view){
       internal val loadingItem = view.infinite_progress_bar_item
       internal val loadingContainer =  view.loading_container


   }


}