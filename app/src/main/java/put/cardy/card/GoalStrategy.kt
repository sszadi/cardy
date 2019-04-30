package put.cardy.card

import put.cardy.model.Goal
import put.cardy.model.Transaction
import java.util.*

interface GoalStrategy {

    fun getGoalValue(goal: Double): String
    fun getActualGoalValue(actualGoal: Double): String
    fun getPopupId(): Int
    fun calculateActualGoal(cardGoal: Goal, transactions: ArrayList<Transaction>): Double


}