package perangkaikode.com.sampletemplatingclass.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_1.view.*
import kotlinx.android.synthetic.main.item_2.view.*
import kotlinx.android.synthetic.main.progressbar.view.*
import perangkaikode.com.sampletemplatingclass.R
import perangkaikode.com.sampletemplatingclass.interfaces.OnTypeClickListener
import perangkaikode.com.sampletemplatingclass.interfaces.PaginationListener
import perangkaikode.com.sampletemplatingclass.model.SampleModel
import perangkaikode.com.sampletemplatingclass.util.GlobalVariable

class MultipleLoadAdapter(
    private val context: Context,
    private var layout: Int,
    private var listItems: List<SampleModel>,
    private val loadWhat: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var positions: Int? = null
    private var isLoading: Boolean = false

    lateinit var listener: OnTypeClickListener
    lateinit var pagination: PaginationListener

    fun initOnClick(listener: OnTypeClickListener) {
        this.listener = listener
    }

    fun initPagination(pagination: PaginationListener) {
        this.pagination = pagination
    }

    companion object {
        private val itemLayout1 = 111
        private val itemLayoutLoading = 222
        var loadType1 = 1
        var loadType2 = 2
        const val TOMBOL_1 = 11
        const val TOMBOL_2 = 22
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return when (position) {
            itemLayout1 -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(layout, viewGroup, false)
                return HolderLayout1(loadWhat, view)
            }
            else -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.progressbar, viewGroup, false)
                HolderLayout2(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HolderLayout1) {
            if (::listener.isInitialized) holder.itemClick(loadWhat, listener)
            holder.bindView(loadWhat, listItems[position])
        }

        if (holder is HolderLayout2) {
            holder.bindView()
        }

        if (::pagination.isInitialized && !isLoading && position + 1 <= itemCount) {
            pagination.loadMore()
            isLoading = true
            positions = itemCount - 1
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == positions) {
            itemLayoutLoading
        } else {
            itemLayout1
        }
    }

    inner class HolderLayout1(loadWhat: Int, view: View) : RecyclerView.ViewHolder(view) {

        var tv1: TextView? = null
        var tv2: TextView? = null

        init {
            when (loadWhat) {
                loadType1 -> {
                    tv1 = view.item_1_tv_1
                    tv2 = view.item_1_tv_2
                }

                loadType2 -> {
                    tv1 = view.item_2_tv_1
                    tv2 = view.item_2_tv_2
                }
            }
        }

        fun itemClick(loadWhat: Int, listener: OnTypeClickListener) {
            tv1?.setOnClickListener { view ->
                listener.onClick(adapterPosition, view, TOMBOL_1)
            }
            tv2?.setOnClickListener { view ->
                listener.onClick(adapterPosition, view, TOMBOL_2)
            }
        }

        fun bindView(loadWhat: Int, item: SampleModel) {
            when (loadWhat) {
                loadType1 -> {
                    tv1?.text = ""
//                    tv2?.text = item.tv1
                }

                loadType2 -> {
                    tv1?.text = ""
//                    tv2?.text = item.tv1
                }
            }
        }
    }

    inner class HolderLayout2(view: View) : RecyclerView.ViewHolder(view) {

        var progress: View? = null

        init {
            progress = view.progressBar1
        }

        fun bindView() {
            if (isLoading && positions == itemCount) {
                progress?.visibility = View.GONE
            }
        }
    }

    fun setLoaded() {
        isLoading = false
        positions = itemCount
    }
}