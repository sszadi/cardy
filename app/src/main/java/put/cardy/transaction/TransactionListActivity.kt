package put.cardy.transaction

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import android.widget.TextView
import put.cardy.R

class TransactionListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_activity)
        loadTransactions()
    }

    private fun loadTransactions() {
        val intent = intent
        val id = intent.getLongExtra("id", 0)
        val listView = findViewById<ListView>(R.id.list_view)
        listView.emptyView = findViewById<TextView>(R.id.empty)
        val transactionList = TransactionRepository(this).findByCardId(id)
        val listAdapter = TransactionListAdapter(this, transactionList)
        listAdapter.notifyDataSetChanged()
        listView.adapter = listAdapter
    }
}