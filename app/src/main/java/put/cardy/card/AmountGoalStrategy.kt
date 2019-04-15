package put.cardy.card

import put.cardy.R
import put.cardy.model.Goal

class AmountGoalStrategy : GoalStrategy {
    override fun manageTransactionAdded(cardGoal: Goal, expense: Double): Double {
        return cardGoal.actualGoal - 1.0
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