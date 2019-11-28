package perangkaikode.com.sampletemplatingclass.adapter.parrent_child

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_parrent.view.*
import kotlinx.android.synthetic.main.progressbar.view.*
import perangkaikode.com.sampletemplatingclass.R
import perangkaikode.com.sampletemplatingclass.interfaces.OnClickListener
import perangkaikode.com.sampletemplatingclass.interfaces.PaginationListener
import perangkaikode.com.sampletemplatingclass.model.SampleModelParrentChild

class ParentAdapter(
    private val context: Context,
    private var listItems: List<SampleModelParrentChild>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var adapter: ChildAdapter? = null
    private var listItemsChild = ArrayList<SampleModelParrentChild.Item>()

    private var positions: Int? = null
    private var isLoading: Boolean = false

    lateinit var listener: OnClickListener
    lateinit var pagination: PaginationListener

    fun initOnClick(listener: OnClickListener) {
        this.listener = listener
    }

    fun initPagination(pagination: PaginationListener) {
        this.pagination = pagination
    }

    companion object {
        private val itemLayout1 = 111
        private val itemLayoutLoading = 222
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): RecyclerView.ViewHolder {
        return when (position) {
            itemLayout1 -> {
                val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_parrent, viewGroup, false)
                return HolderLayout1(view)
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
            holder.setupRecyclerView(listItems[position])
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

    inner class HolderLayout1(view: View) : RecyclerView.ViewHolder(view), OnClickListener {

        var tv1: TextView? = view.tv_1_parrent
        var rvChild: RecyclerView? = view.rv_child

        fun itemClick(listener: OnClickListener) {
            itemView.setOnClickListener { view ->
                listener.onClick(adapterPosition, view)
            }
        }

        fun bindView(item: SampleModelParrentChild) {
            tv1?.text = ""
        }

        fun setupRecyclerView(item: SampleModelParrentChild) {
            rvChild?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            listItemsChild = item.items as ArrayList<SampleModelParrentChild.Item>
            adapter = ChildAdapter(context, R.layout.item_child, listItemsChild)
            adapter!!.initAction(this)
            rvChild?.adapter = adapter
        }

        override fun onClick(position: Int, view: View) {
            /**/
        }
    }

    inner class HolderLayout2(view: View) : RecyclerView.ViewHolder(view) {

        var progress: View? = view.progressBar1

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