package perangkaikode.com.sampletemplatingclass.adapter.parrent_child

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_child.view.*
import kotlinx.android.synthetic.main.item_parrent.view.*
import perangkaikode.com.sampletemplatingclass.interfaces.OnClickListener
import perangkaikode.com.sampletemplatingclass.model.SampleModelParrentChild

class ChildAdapter(
    private val context: Context,
    private var layout: Int,
    private var listItems: List<SampleModelParrentChild.Item>
) : RecyclerView.Adapter<ChildAdapter.ViewHolder>() {

    lateinit var listener: OnClickListener

    fun initAction(listener: OnClickListener) {
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

        var tv1: TextView? = view.tv_1_child
        var tv2: TextView? = view.tv_2_child

        fun itemClick(listener: OnClickListener) {
            itemView.setOnClickListener { view ->
                listener.onClick(adapterPosition, view)
            }
        }

        fun bindView(item: SampleModelParrentChild.Item) {
            tv1?.text = ""

        }
    }
}