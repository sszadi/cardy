package put.cardy.card

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Menu
import android.widget.EditText
import kotlinx.android.synthetic.main.add_card.*
import kotlinx.android.synthetic.main.card_controllers.*
import put.cardy.MainActivity
import put.cardy.R
import put.cardy.database.CardRepository
import put.cardy.model.Card

class AddCardAtivity : AppCompatActivity() {

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

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
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
