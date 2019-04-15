package put.cardy.transaction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import put.cardy.R
import put.cardy.model.Transaction

class TransactionListAdapter(
    context: Context,
    private val dataSource: ArrayList<Transaction>
) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val currentTransaction = getItem(position) as Transaction
        val rowView = inflater.inflate(R.layout.list_item_transaction, parent, false)
        val date = rowView.findViewById(R.id.date) as TextView
        val expense = rowView.findViewById(R.id.expense) as TextView
        date.text = currentTransaction.date.toString()
        expense.text = currentTransaction.expense.toString()
        return rowView
    }
}