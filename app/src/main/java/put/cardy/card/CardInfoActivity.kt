package put.cardy.card

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.widget.Spinner
import kotlinx.android.synthetic.main.card_controllers.*
import kotlinx.android.synthetic.main.card_info.*
import put.cardy.R
import put.cardy.database.CardRepository
import put.cardy.database.GoalRepository
import put.cardy.model.Card
import put.cardy.model.Goal
import put.cardy.model.GoalType


class CardInfoActivity : AppCompatActivity() {

    var card: Card = Card()
    var cardGoal: Goal = Goal()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_info)

        disableInputs()
        getCardInfo()

        fab.setOnClickListener {

        }

    }

    private fun disableInputs() {
        cardNumber.inputType = InputType.TYPE_NULL
        bankName.inputType = InputType.TYPE_NULL
        timeSpinner.isEnabled = false
        typeSpinner.isEnabled = false
        goal.inputType = InputType.TYPE_NULL
        actualGoal.inputType = InputType.TYPE_NULL


    }

    private fun getCardInfo() {
        val intent = intent
        val id = intent.getLongExtra("id", 0)
        card = CardRepository(this).findById(id)
        cardGoal = GoalRepository(this).findByCardId(card.id)

        cardNumber.setText(card.number)
        bankName.setText(card.bankName)
        typeSpinner.setSelection(getIndex(typeSpinner, cardGoal.type!!.name))
        timeSpinner.setSelection(getIndex(timeSpinner, cardGoal.period.toString()))
        goal.setText(cardGoal.goal.toString())
        actualGoal.setText(cardGoal.actualGoal.toString())
    }

    private fun getIndex(spinner: Spinner, myString: String): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(myString, ignoreCase = true)) {
                return i
            }
        }

        return 0
    }

}