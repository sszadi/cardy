package put.cardy.card

import put.cardy.R
import put.cardy.model.Goal

class ExpenseGoalStrategy : GoalStrategy {
    override fun manageTransactionAdded(cardGoal: Goal, expense: Double): Double {
        return cardGoal.actualGoal - expense.toDouble()
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