package perangkaikode.com.sampletemplatingclass.sample_pagination.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_1.view.*
import kotlinx.android.synthetic.main.progressbar.view.*
import perangkaikode.com.sampletemplatingclass.R
import perangkaikode.com.sampletemplatingclass.sample_adapter.interfaces.OnTypeClickListener
import perangkaikode.com.sampletemplatingclass.sample_adapter.interfaces.PaginationListener
import perangkaikode.com.sampletemplatingclass.sample_pagination.model.Student

class SamplePaginationAdapter(
    private val context: Context,
    private var listItems: List<Student>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var positions: Int? = null
    private var isLoading: Boolean = false

    lateinit var listener: OnTypeClickListener
    lateinit var pagination: PaginationListener

    companion object {
        private val itemLayout1 = 1
        private val itemLayoutLoading = 2
        const val TOMBOL_1 = 11
        const val TOMBOL_2 = 22
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return when (position) {
            itemLayout1 -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_1, viewGroup, false)
                HolderLayout1(view)
            }
            else -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.progressbar, viewGroup, false)
                HolderLayout2(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HolderLayout1) {
            if (::listener.isInitialized) holder.itemClick(listener)
            holder.bindView(listItems[position])
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

    inner class HolderLayout1(view: View) : RecyclerView.ViewHolder(view) {

        private var tv1: TextView? = view.item_1_tv_1
        private var tv2: TextView? = view.item_1_tv_2

        fun itemClick(listener: OnTypeClickListener) {
            tv1?.setOnClickListener {
                listener.onClick(adapterPosition, it, TOMBOL_1)
            }
            tv2?.setOnClickListener {
                listener.onClick(adapterPosition, it, TOMBOL_2)
            }
        }

        fun bindView(item: Student) {
            tv1?.text = item.name
            tv2?.text = item.emailId
        }
    }

    inner class HolderLayout2(view: View) : RecyclerView.ViewHolder(view) {

        private var progress: View? = view.progressBar1

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