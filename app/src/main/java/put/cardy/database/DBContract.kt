package put.cardy.database

import android.provider.BaseColumns

object DBContract {

    class DBEntry : BaseColumns {
        companion object {
            const val ID = "id"
            const val CARD_ID = "cardId"
            const val DATE = "date"
        }

    }


    class CardEntry : BaseColumns {
        companion object {
            const val CARD_TABLE_NAME = "CARDS"
            const val NUMBER = "number"
            const val BANK_NAME = "bankName"
        }

    }

    class TransactionEntry : BaseColumns {
        companion object {
            const val TRANSACTION_TABLE_NAME = "TRANSACTION_TABLE"
            const val EXPENSE = "expense"
        }

    }

    class GoalEntry : BaseColumns {
        companion object {
            const val GOAL_TABLE_NAME = "GOAL"
            const val TYPE = "type"
            const val PERIOD = "period"
            const val GOAL = "goal"
            const val ACTUAL_GOAL = "actualGoal"
        }

    }

}