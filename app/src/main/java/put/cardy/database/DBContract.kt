package put.cardy.database

import android.provider.BaseColumns

object DBContract {

    class CardEntry : BaseColumns {
        companion object {
            const val TABLE_NAME = "Cards"
            const val CARD_ID = "id"
            const val NUMBER = "number"
            const val BANK_NAME = "bankName"
            const val TYPE = "type"
            const val TIME = "time"
            const val GOAL = "goal"
            const val ACTUAL_GOAL = "actualGoal"
        }

    }
}