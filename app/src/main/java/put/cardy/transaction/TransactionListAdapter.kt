package put.cardy.transaction

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import put.cardy.DateFromatter
import put.cardy.R
import put.cardy.model.Transaction

class TransactionListAdapter(
    private val context: Context,
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
        val deleteButton = rowView.findViewById<ImageView>(R.id.delete)
        deleteButton.setOnClickListener { manageItemDelete(position, currentTransaction) }
        date.text = DateFromatter.format(currentTransaction.date)
        manageTextView(currentTransaction.expense.toString(), expense)
        return rowView
    }

    private fun manageTextView(text: String, textView: TextView) {
        if (text != "0.0")
            textView.text = text
        else
            textView.visibility = View.INVISIBLE
    }

    private fun manageItemDelete(position: Int, currentTransaction: Transaction) {
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    deleteItem(position, currentTransaction)
                }
            }
        }

        val builder = AlertDialog.Builder(context)
        builder.setMessage(context.getString(R.string.delete_record_info))
            .setPositiveButton(context.getString(R.string.yes), dialogClickListener)
            .setNegativeButton(context.getString(R.string.no), dialogClickListener).show()
    }

    private fun deleteItem(position: Int, transaction: Transaction) {
        dataSource.removeAt(position)
        TransactionRepository(context).delete(transaction)
        notifyDataSetChanged()
    }
}