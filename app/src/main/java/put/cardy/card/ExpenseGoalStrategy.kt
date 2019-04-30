package put.cardy.card

import put.cardy.R
import put.cardy.model.Goal
import put.cardy.model.Transaction
import java.util.*

class ExpenseGoalStrategy : GoalStrategy {
    override fun calculateActualGoal(cardGoal: Goal, transactions: ArrayList<Transaction>): Double {
        cardGoal.actualGoal = cardGoal.goal
        transactions.forEach {
            if (cardGoal.actualGoal != 0.0) {
                cardGoal.actualGoal -= it.expense!!
            }
        }
        return cardGoal.actualGoal
    }

    override fun getPopupId(): Int {
        return R.layout.expense_popup
    }

    override fun getGoalValue(goal: Double): String {
        return goal.toString()
    }

    override fun getActualGoalValue(actualGoal: Double): String {
        return actualGoal.toString()
    }

}