package put.cardy.database

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import put.cardy.R
import put.cardy.model.Card


class CardListAdapter(
    private val context: Context,
    private val dataSource: ArrayList<Card>
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
        val currentCard = getItem(position) as Card
        val rowView = inflater.inflate(R.layout.list_item_card, parent, false)
        val cardNumberView = rowView.findViewById(R.id.cardNumber) as TextView
        val bankNameView = rowView.findViewById(R.id.bankName) as TextView
        val deleteButton = rowView.findViewById<ImageView>(R.id.delete)
        deleteButton.setOnClickListener { manageItemDelete(position, currentCard) }
        cardNumberView.text = currentCard.number
        bankNameView.text = currentCard.bankName

        return rowView
    }

    private fun manageItemDelete(position: Int, currentCard: Card) {
        val dialogClickListener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    deleteItem(position, currentCard)
                }
            }
        }

        val builder = AlertDialog.Builder(context)
        builder.setMessage(context.getString(R.string.delete_record_info))
            .setPositiveButton(context.getString(R.string.yes), dialogClickListener)
            .setNegativeButton(context.getString(R.string.no), dialogClickListener).show()
    }

    private fun deleteItem(position: Int, currentCard: Card) {
        dataSource.removeAt(position)
        CardRepository(context).delete(currentCard)
        notifyDataSetChanged()
    }
}