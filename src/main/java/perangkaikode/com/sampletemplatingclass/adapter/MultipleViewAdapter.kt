package perangkaikode.com.sampletemplatingclass.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_1.view.*
import kotlinx.android.synthetic.main.item_2.view.*
import kotlinx.android.synthetic.main.progressbar.view.*
import perangkaikode.com.sampletemplatingclass.R
import perangkaikode.com.sampletemplatingclass.interfaces.OnClickListener
import perangkaikode.com.sampletemplatingclass.interfaces.OnTypeClickListener
import perangkaikode.com.sampletemplatingclass.interfaces.PaginationListener
import perangkaikode.com.sampletemplatingclass.model.SampleModel
import perangkaikode.com.sampletemplatingclass.util.GlobalVariable

class MultipleViewAdapter(
    private val context: Context,
    private var listItems: List<SampleModel>
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
        private val itemLayout1 = 1
        private val itemLayout2 = 2
        private val itemLayoutLoading = 3
        const val TOMBOL_1 = 11
        const val TOMBOL_2 = 22
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return when (position) {
            itemLayout1 -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_1, viewGroup, false)
                HolderLayout1(view)
            }
            itemLayout2 -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_2, viewGroup, false)
                HolderLayout2(view)
            }
            else -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.progressbar, viewGroup, false)
                HolderLayout3(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HolderLayout1) {
            if (::listener.isInitialized) holder.itemClick(listener)
            holder.bindView(listItems[position])
        }

        if (holder is HolderLayout2) {
            if (::listener.isInitialized) holder.itemClick(listener)
            holder.bindView(listItems[position])
        }

        if (holder is HolderLayout3) {
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
            val item = listItems[position]
            when {
//                item.name.equals("layout_1") -> itemLayout1
                else -> itemLayout2
            }
        }
    }

    inner class HolderLayout1(view: View) : RecyclerView.ViewHolder(view) {

        var tv1: TextView? = null
        var tv2: TextView? = null

        init {
            tv1 = view.item_1_tv_1
            tv2 = view.item_1_tv_2
        }

        fun itemClick(listener: OnTypeClickListener) {
            tv1?.setOnClickListener { view ->
                listener.onClick(adapterPosition, view, TOMBOL_1)
            }
            tv2?.setOnClickListener { view ->
                listener.onClick(adapterPosition, view, TOMBOL_2)
            }
        }

        fun bindView(item: SampleModel) {
            tv1?.text = ""
//            tv2?.text = item.tv1
        }
    }

    inner class HolderLayout2(view: View) : RecyclerView.ViewHolder(view) {

        var tv1: TextView? = null
        var tv2: TextView? = null

        init {
            tv1 = view.item_2_tv_1
            tv2 = view.item_2_tv_2
        }

        fun itemClick(listener: OnTypeClickListener) {
            tv1?.setOnClickListener { view ->
                listener.onClick(adapterPosition, view, TOMBOL_1)
            }
            tv2?.setOnClickListener { view ->
                listener.onClick(adapterPosition, view, TOMBOL_2)
            }
        }

        fun bindView(item: SampleModel) {
            tv1?.text = ""
//            tv2?.text = item.tv1
        }
    }

    inner class HolderLayout3(view: View) : RecyclerView.ViewHolder(view) {

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