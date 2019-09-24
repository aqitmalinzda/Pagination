package perangkaikode.com.sampletemplatingclass.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_1.view.*
import perangkaikode.com.sampletemplatingclass.interfaces.OnClickListener
import perangkaikode.com.sampletemplatingclass.interfaces.PaginationListener
import perangkaikode.com.sampletemplatingclass.model.SampleModel
import perangkaikode.com.sampletemplatingclass.util.GlobalVariable

class SingleItemWithoutPaginationAdapter(
    private val context: Context,
    private var layout: Int,
    private var listItems: List<SampleModel>
) : RecyclerView.Adapter<SingleItemWithoutPaginationAdapter.ViewHolder>() {

    lateinit var listener: OnClickListener

    fun initOnClick(listener: OnClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (::listener.isInitialized) holder.itemClick(listener)

        holder.bindView(listItems[position])
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tv1: TextView? = null
        var tv2: TextView? = null

        init {
            tv1 = view.item_1_tv_1
            tv2 = view.item_1_tv_2
        }

        fun itemClick(listener: OnClickListener) {
            itemView.setOnClickListener { view ->
                listener.onClick(adapterPosition, view)
            }
        }

        fun bindView(item: SampleModel) {
            tv1?.text = ""
//            tv2?.text = item.tv1
        }
    }
}