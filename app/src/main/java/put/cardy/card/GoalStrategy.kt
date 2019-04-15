package put.cardy.card

import put.cardy.model.Goal

interface GoalStrategy {

    fun getGoalValue(goal: Double): String
    fun getActualGoalValue(actualGoal: Double): String
    fun getPopupId(): Int
    fun manageTransactionAdded(cardGoal: Goal, expense: Double): Double


}