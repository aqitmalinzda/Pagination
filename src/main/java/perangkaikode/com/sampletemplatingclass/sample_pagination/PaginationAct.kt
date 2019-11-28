package perangkaikode.com.sampletemplatingclass.sample_pagination

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_pagination.*
import perangkaikode.com.sampletemplatingclass.R
import perangkaikode.com.sampletemplatingclass.sample_pagination.adapter.SamplePaginationAdapter
import perangkaikode.com.sampletemplatingclass.interfaces.OnTypeClickListener
import perangkaikode.com.sampletemplatingclass.interfaces.PaginationListener
import perangkaikode.com.sampletemplatingclass.sample_pagination.model.Student
import java.util.ArrayList

class PaginationAct : AppCompatActivity(), OnTypeClickListener, PaginationListener {

    private var mAdapter: SamplePaginationAdapter? = null
    private var mLayoutManager: LinearLayoutManager? = null
    private var studentList: ArrayList<Student> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagination)

        setupRecyclerView()
        loadData(false)
    }

    private fun setupRecyclerView() {
        mLayoutManager = LinearLayoutManager(this)
        my_recycler_view.setLayoutManager(mLayoutManager)
        mAdapter =
            SamplePaginationAdapter(
                this,
                studentList
            )
        mAdapter?.initOnClick(this)
        mAdapter?.initPagination(this)
        my_recycler_view.setAdapter(mAdapter)
    }

    private fun loadData(loadMore: Boolean) {
        if (loadMore) {
            val start = studentList.size
            val end = start + 25
            for (i in start + 1..end) {
                studentList.add(
                    Student(
                        "Student $i",
                        "AndroidStudent$i@gmail.com"
                    )
                )
            }
            mAdapter?.notifyItemRangeInserted(mAdapter?.itemCount!!, studentList.size)
        } else {
            for (i in 1..25) {
                studentList.add(
                    Student(
                        "Student $i",
                        "androidstudent$i@gmail.com"
                    )
                )
            }
            mAdapter?.notifyItemRangeInserted(mAdapter?.itemCount!!, studentList.size)
        }
        mAdapter?.setLoaded()
        mAdapter?.notifyDataSetChanged()
    }

    override fun onClick(position: Int, view: View, button: Int) {
        /**/
    }

    override fun loadMore() {
        Handler().postDelayed({
            loadData(true)
        }, 5000)
    }
}
