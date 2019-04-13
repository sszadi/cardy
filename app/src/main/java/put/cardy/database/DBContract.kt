package put.cardy.database

import android.provider.BaseColumns

object DBContract {

    class CardEntry : BaseColumns {
        companion object {
            const val CARD_TABLE_NAME = "CARDS"
            const val ID = "id"
            const val NUMBER = "number"
            const val BANK_NAME = "bankName"
        }

    }

    class GoalEntry : BaseColumns {
        companion object {
            const val GOAL_TABLE_NAME = "GOAL"
            const val ID = "id"
            const val CARD_ID = "cardId"
            const val TYPE = "type"
            const val PERIOD = "period"
            const val GOAL = "goal"
            const val ACTUAL_GOAL = "actualGoal"
            const val DATE = "date"
        }

    }

}