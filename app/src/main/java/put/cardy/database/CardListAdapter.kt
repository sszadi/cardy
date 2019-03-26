package put.cardy.database

import android.content.Context
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
        cardNumberView.text = currentCard.number
        bankNameView.text = currentCard.bankName
        return rowView
    }
}