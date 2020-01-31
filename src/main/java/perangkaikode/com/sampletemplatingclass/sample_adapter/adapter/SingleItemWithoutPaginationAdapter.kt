package perangkaikode.com.sampletemplatingclass.sample_adapter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_1.view.*
import perangkaikode.com.sampletemplatingclass.sample_adapter.interfaces.OnClickListener
import perangkaikode.com.sampletemplatingclass.sample_adapter.model.SampleModel

class SingleItemWithoutPaginationAdapter(
    private val context: Context,
    private var layout: Int,
    private var listItems: List<SampleModel>
) : RecyclerView.Adapter<SingleItemWithoutPaginationAdapter.ViewHolder>() {

    lateinit var listener: OnClickListener

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

        private var tv1: TextView? = view.item_1_tv_1
        private var tv2: TextView? = view.item_1_tv_2

        fun itemClick(listener: OnClickListener) {
            itemView.setOnClickListener {
                listener.onClick(adapterPosition, it)
            }
        }

        fun bindView(item: SampleModel) {
            tv1?.text = ""
//            tv2?.text = item.tv1
        }
    }
}