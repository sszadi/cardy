package put.cardy.card

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import kotlinx.android.synthetic.main.add_card.*
import kotlinx.android.synthetic.main.card_controllers.*
import org.joda.time.DateTime
import put.cardy.FieldValidator.Companion.validateField
import put.cardy.MainActivity
import put.cardy.R
import put.cardy.database.CardRepository
import put.cardy.database.GoalRepository
import put.cardy.model.Card
import put.cardy.model.Goal
import put.cardy.model.GoalType
import put.cardy.model.Period


class AddCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_card)

        addCardButton.setOnClickListener {
            createCard()
        }
    }

    private fun createCard() {
        val number = cardNumber.text.toString()
        val name = bankName.text.toString()
        val type = typeSpinner.selectedItem.toString()
        val period = timeSpinner.selectedItem.toString()
        val goalValue = goal.text.toString()


        if (isProvidedCardValid(number, cardNumber, name, bankName, goalValue, goal)) {
            val newCard = Card(0, number, name)
            val cardId = CardRepository(this).create(newCard)

            val newGoal = Goal(
                0,
                cardId,
                GoalType.getTypeByText(type),
                Period.valueOf(period.toUpperCase()),
                goalValue.toDouble(),
                goalValue.toDouble(),
                DateTime.now()
            )
            GoalRepository(this).create(newGoal)
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun isProvidedCardValid(
        number: String,
        cardNumber: EditText,
        name: String,
        bankName: EditText,
        goalValue: String,
        goal: EditText
    ): Boolean {
        return validateField(number, cardNumber) && validateField(name, bankName) && validateField(goalValue, goal)
    }


}
