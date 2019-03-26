package put.cardy.card

import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.widget.EditText
import kotlinx.android.synthetic.main.add_card.*
import put.cardy.BaseActivityWithToolbar
import put.cardy.R
import put.cardy.database.CardRepository
import put.cardy.model.Card

class AddCardAtivity : BaseActivityWithToolbar() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_card)

        addCardButton.setOnClickListener {
            createCard()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun createCard() {
        val number = cardNumber.text.toString()
        val name = bankName.text.toString()
        val type = termTypeSpinner.selectedItem.toString()
        val time = termTimeSpinner.selectedItem.toString()
        val goal = goal.text.toString()


        if (isProvidedCardValid(number)) {
            val newCard = Card(0, number, name, type, time, goal.toDouble(), goal.toDouble())
            CardRepository(this).create(newCard)
        }
    }

    private fun isProvidedCardValid(number: String): Boolean {
        return validateFields(number, cardNumber) && validateFields(number, cardNumber)
    }

    private fun validateFields(value: String, field: EditText): Boolean {
        if (TextUtils.isEmpty(value)) {
            field.error = "Field is required!"
            return false
        }
        return true
    }


}
