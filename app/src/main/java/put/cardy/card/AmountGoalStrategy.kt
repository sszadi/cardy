package put.cardy.card

import put.cardy.R
import put.cardy.model.Goal
import put.cardy.model.Transaction
import java.util.*

class AmountGoalStrategy : GoalStrategy {
    override fun manageTransactionAdded(cardGoal: Goal, transactions: ArrayList<Transaction>): Double {
        cardGoal.actualGoal = cardGoal.goal
        repeat(transactions.size) {
            if (cardGoal.actualGoal != 0.0) {
                cardGoal.actualGoal -= 1.0
            }
        }
        return cardGoal.actualGoal
    }

    override fun getPopupId(): Int {
        return R.layout.amount_popup
    }

    override fun getActualGoalValue(actualGoal: Double): String {
        return actualGoal.toInt().toString()
    }

    override fun getGoalValue(goal: Double): String {
        return goal.toInt().toString()
    }


}