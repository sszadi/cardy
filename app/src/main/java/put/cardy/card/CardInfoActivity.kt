package put.cardy.card

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.transition.TransitionManager
import android.view.*
import android.widget.*
import kotlinx.android.synthetic.main.card_controllers.*
import kotlinx.android.synthetic.main.card_info.*
import org.joda.time.DateTime
import put.cardy.DateFromatter
import put.cardy.FieldValidator.Companion.validateField
import put.cardy.R
import put.cardy.database.CardRepository
import put.cardy.database.GoalRepository
import put.cardy.model.Card
import put.cardy.model.Goal
import put.cardy.model.GoalType
import put.cardy.model.Transaction
import put.cardy.transaction.TransactionListActivity
import put.cardy.transaction.TransactionRepository


class CardInfoActivity : AppCompatActivity() {

    var card: Card = Card()
    private var cardGoal: Goal = Goal()
    private var goalStrategy: GoalStrategy = AmountGoalStrategy()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_info)

        disableInputs()

        fab.setOnClickListener {
            openPopup()
        }

    }

    override fun onResume() {
        super.onResume()
        getCardInfo()

    }

    private fun openPopup() {
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(goalStrategy.getPopupId(), null)

        val popupWindow = PopupWindow(
            view,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        popupWindow.elevation = 10.0F
        popupWindow.isFocusable = true
        popupWindow.update()
        popupWindow.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;


        val date = view.findViewById<EditText>(R.id.date)
        date.inputType = InputType.TYPE_NULL
        date.setText(getActualDate())

        val addButton = view.findViewById<Button>(R.id.button_popup)
        val closeButton = view.findViewById<Button>(R.id.close_button)

        val expenseField = view.findViewById<EditText>(R.id.expense)


        closeButton.setOnClickListener {
            popupWindow.dismiss()
        }

        addButton.setOnClickListener {
            var expense = 0.0
            expenseField?.let {
                expense = expenseField.text.toString().toDouble()
            }
            if (validateField(expense.toString(), expenseField)) {
                TransactionRepository(this).create(Transaction(0, card.id, DateTime.now(), expense))
                cardGoal.actualGoal = calculateActualGoal()
                GoalRepository(context = this).update(cardGoal)
                getGoalInfo(cardGoal)
            }
            popupWindow.dismiss()
        }

        TransitionManager.beginDelayedTransition(root_layout)
        popupWindow.showAtLocation(
            root_layout,
            Gravity.BOTTOM,
            0,
            0
        )

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_list -> {
                val intent = Intent(this, TransactionListActivity::class.java)
                intent.putExtra("id", card.id)
                this.startActivity(intent)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun getActualDate(): String? {
        return DateFromatter.format(DateTime.now())
    }

    private fun calculateActualGoal(): Double {
        val transactionList = TransactionRepository(context = this).findByCardId(card.id)
        return goalStrategy.calculateActualGoal(cardGoal, transactionList)
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
        cardGoal = GoalRepository(this).findByCardId(id)
        getGoalInfo(cardGoal)
        cardNumber.setText(card.number)
        bankName.setText(card.bankName)

    }

    private fun getGoalInfo(currentGoal: Goal) {
        resolveGoalStrategy(currentGoal.type)
        typeSpinner.setSelection(getIndex(typeSpinner, currentGoal.type!!.name))
        timeSpinner.setSelection(getIndex(timeSpinner, currentGoal.period.toString()))
        goal.setText(goalStrategy.getGoalValue(currentGoal.goal))
        actualGoal.setText(goalStrategy.getActualGoalValue(calculateActualGoal()))
    }

    private fun resolveGoalStrategy(type: GoalType?) {
        goalStrategy = if (GoalType.AMOUNT == type) AmountGoalStrategy() else ExpenseGoalStrategy()
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