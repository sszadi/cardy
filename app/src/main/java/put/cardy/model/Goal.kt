package put.cardy.model

import org.joda.time.DateTime

open class Goal constructor(
    var id: Long = 0,
    var cardId: Long = 0,
    var type: GoalType? = GoalType.AMOUNT,
    var period: Period = Period.MONTH,
    var goal: Double = 0.0,
    var actualGoal: Double = 0.0,
    var date: DateTime = DateTime.now()

)