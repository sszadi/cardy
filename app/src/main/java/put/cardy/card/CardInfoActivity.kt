package put.cardy.card

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.widget.Spinner
import kotlinx.android.synthetic.main.card_controllers.*
import put.cardy.R
import put.cardy.database.CardRepository


class CardInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_info)

        disableInputs()
        getCardInfo();
    }

    private fun disableInputs() {
        cardNumber.inputType = InputType.TYPE_NULL;
        bankName.inputType = InputType.TYPE_NULL;
        termTimeSpinner.visibility = 0;
        termTimeSpinner.visibility = 0;
        goal.inputType = InputType.TYPE_NULL;


    }

    private fun getCardInfo() {
        val intent = intent
        val id = intent.getIntExtra("id", 0)
        val card = CardRepository(this).findById(id)
        cardNumber.setText(card.number)
        bankName.setText(card.bankName)
        termTypeSpinner.setSelection(getIndex(termTypeSpinner, card.type))
        termTimeSpinner.setSelection(getIndex(termTimeSpinner, card.time))
        goal.setText(card.goal.toString())
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