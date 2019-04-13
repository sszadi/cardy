package put.cardy.database

import android.content.Context
import org.jetbrains.anko.db.*
import org.joda.time.DateTime
import put.cardy.database.DBContract.GoalEntry.Companion.GOAL_TABLE_NAME
import put.cardy.model.Goal
import put.cardy.model.GoalType
import put.cardy.model.Period
import java.util.*


class GoalRepository(val context: Context) {

    fun findAll(): ArrayList<Goal> = context.database.use {
        val expenseGoalList = ArrayList<Goal>()

        select(
            DBContract.GoalEntry.GOAL_TABLE_NAME,
            DBContract.GoalEntry.ID,
            DBContract.GoalEntry.CARD_ID,
            DBContract.GoalEntry.TYPE,
            DBContract.GoalEntry.PERIOD,
            DBContract.GoalEntry.GOAL,
            DBContract.GoalEntry.ACTUAL_GOAL,
            DBContract.GoalEntry.DATE

        )
            .parseList(object : MapRowParser<List<Goal>> {
                override fun parseRow(columns: Map<String, Any?>): List<Goal> {
                    val id = columns.getValue(DBContract.GoalEntry.ID)
                    val cardId = columns.getValue(DBContract.GoalEntry.CARD_ID)
                    val type = columns.getValue(DBContract.GoalEntry.TYPE)
                    val period = columns.getValue(DBContract.GoalEntry.PERIOD)
                    val goal = columns.getValue(DBContract.GoalEntry.GOAL)
                    val actualGoal = columns.getValue(DBContract.GoalEntry.ACTUAL_GOAL)
                    val date = columns.getValue(DBContract.GoalEntry.DATE)

                    val goalEntry = Goal(
                        id.toString().toLong(),
                        cardId.toString().toLong(),
                        GoalType.valueOf(type.toString()),
                        Period.valueOf(period.toString()),
                        goal.toString().toDouble(),
                        actualGoal.toString().toDouble(),
                        DateTime(date)
                    )

                    expenseGoalList.add(goalEntry)

                    return expenseGoalList
                }
            })

        expenseGoalList
    }

    fun create(goal: Goal) = context.database.use {
        insert(
            DBContract.GoalEntry.GOAL_TABLE_NAME,
            DBContract.GoalEntry.CARD_ID to goal.cardId,
            DBContract.GoalEntry.TYPE to goal.type.toString(),
            DBContract.GoalEntry.PERIOD to goal.period.toString(),
            DBContract.GoalEntry.GOAL to goal.goal,
            DBContract.GoalEntry.ACTUAL_GOAL to goal.actualGoal,
            DBContract.GoalEntry.DATE to goal.date.toString()
        )
    }

    fun findByCardId(id: Long): Goal = context.database.use {
        var goalFromDb = Goal()
        select(
            GOAL_TABLE_NAME,
            DBContract.GoalEntry.ID,
            DBContract.GoalEntry.CARD_ID,
            DBContract.GoalEntry.TYPE,
            DBContract.GoalEntry.PERIOD,
            DBContract.GoalEntry.GOAL,
            DBContract.GoalEntry.ACTUAL_GOAL,
            DBContract.GoalEntry.DATE
        ).whereArgs("id = {id}", "id" to id)
            .parseSingle(object : MapRowParser<Goal> {
                override fun parseRow(columns: Map<String, Any?>): Goal {
                    val goalId = columns.getValue(DBContract.GoalEntry.ID)
                    val cardId = columns.getValue(DBContract.GoalEntry.CARD_ID)
                    val type = columns.getValue(DBContract.GoalEntry.TYPE)
                    val period = columns.getValue(DBContract.GoalEntry.PERIOD)
                    val goal = columns.getValue(DBContract.GoalEntry.GOAL)
                    val actualGoal = columns.getValue(DBContract.GoalEntry.ACTUAL_GOAL)
                    val date = columns.getValue(DBContract.GoalEntry.DATE)

                    goalFromDb = Goal(
                        goalId.toString().toLong(),
                        cardId.toString().toLong(),
                        GoalType.valueOf(type.toString()),
                        Period.valueOf(period.toString()),
                        goal.toString().toDouble(),
                        actualGoal.toString().toDouble(),
                        DateTime(date)
                    )

                    return goalFromDb
                }
            })

        goalFromDb
    }

    fun update(goal: Goal) = context.database.use {
        update(
            DBContract.GoalEntry.GOAL_TABLE_NAME,
            DBContract.GoalEntry.CARD_ID to goal.cardId,
            DBContract.GoalEntry.TYPE to goal.type,
            DBContract.GoalEntry.PERIOD to goal.period,
            DBContract.GoalEntry.GOAL to goal.goal,
            DBContract.GoalEntry.ACTUAL_GOAL to goal.actualGoal,
            DBContract.GoalEntry.DATE to goal.date

        )
            .whereArgs("id = {id}", "id" to goal.id)
            .exec()
    }

    fun delete(goal: Goal) = context.database.use {
        delete(
            DBContract.GoalEntry.GOAL_TABLE_NAME,
            whereClause = "id = {id}",
            args = *arrayOf("id" to goal.id)
        )
    }
}