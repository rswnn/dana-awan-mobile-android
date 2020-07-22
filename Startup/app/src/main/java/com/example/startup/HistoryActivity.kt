package com.example.startup

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.startup.models.History
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.history_list.view.*

class HistoryActivity : AppCompatActivity() {

    var listOfHistory = ArrayList<History>()
    var adapter:ListHistoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        listOfHistory.add(History("Terkirim", "Kamu Mengirim", 10000))
        listOfHistory.add(History("Terkirim", "Kamu Mengirim", 10000))
        listOfHistory.add(History("Menerima", "Kamu Menerima", 100000))
        listOfHistory.add(History("Menerima", "Kamu Menerima", 50000))
        listOfHistory.add(History("Terkirim", "Kamu Mengirim", 30000))
        adapter = ListHistoryAdapter(this, listOfHistory)
        println("----------------------------------------------------------------------")
        println(listOfHistory)
        lvHistory?.adapter =adapter
    }

    inner class ListHistoryAdapter: BaseAdapter {
        var listHistory =ArrayList<History>()
        var context: Context? = null
        constructor(context: Context, listHistory: ArrayList<History>):super() {
            this.listHistory = listHistory
            this.context = context
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val history = listHistory[position]
            var inflantor = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val historyView = inflantor.inflate(R.layout.history_list, null)
            historyView.tvHistoryMessage.text = history.message!!
            historyView.tvHistoryDesc.text = history.desc!!
            historyView.tvHistoryAmount.text = "Rp." +  history.amount!!.toString()

            return historyView
        }

        override fun getItem(position: Int): Any {
            return listHistory[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listHistory.size
        }

    }
}
