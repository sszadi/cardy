package put.cardy.database

import android.provider.BaseColumns

object DBContract {

    class CardEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "cards"
            val CARD_ID = "id"
            val NUMBER = "number"
            val BANKNAME = "bankName"
        }

    }
}