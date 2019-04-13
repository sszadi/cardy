package put.cardy.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import put.cardy.database.DBContract.CardEntry.Companion.BANK_NAME
import put.cardy.database.DBContract.CardEntry.Companion.CARD_TABLE_NAME
import put.cardy.database.DBContract.CardEntry.Companion.NUMBER
import put.cardy.database.DBContract.GoalEntry.Companion.ACTUAL_GOAL
import put.cardy.database.DBContract.GoalEntry.Companion.DATE
import put.cardy.database.DBContract.GoalEntry.Companion.GOAL
import put.cardy.database.DBContract.GoalEntry.Companion.GOAL_TABLE_NAME
import put.cardy.database.DBContract.GoalEntry.Companion.PERIOD
import put.cardy.database.DBContract.GoalEntry.Companion.TYPE


class CardDatabaseHandler(ctx: Context) : ManagedSQLiteOpenHelper(ctx, DBContract.CardEntry.CARD_TABLE_NAME, null, 1) {
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(DBContract.CardEntry.CARD_TABLE_NAME, true)
    }

    companion object {
        private var instance: CardDatabaseHandler? = null
        @Synchronized
        fun getInstance(ctx: Context): CardDatabaseHandler {
            if (instance == null) {
                instance = CardDatabaseHandler(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        createCardTable(db)
        createGoalTable(db)
    }

    private fun createGoalTable(db: SQLiteDatabase) {
        db.createTable(
            GOAL_TABLE_NAME, true,
            DBContract.GoalEntry.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            DBContract.GoalEntry.CARD_ID to INTEGER + NOT_NULL,
            TYPE to TEXT + NOT_NULL,
            PERIOD to TEXT + NOT_NULL,
            GOAL to REAL + NOT_NULL,
            ACTUAL_GOAL to REAL + NOT_NULL,
            DATE to TEXT + NOT_NULL
        )
    }

    private fun createCardTable(db: SQLiteDatabase) {
        db.createTable(
            CARD_TABLE_NAME, true,
            DBContract.CardEntry.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            NUMBER to TEXT + NOT_NULL,
            BANK_NAME to TEXT + NOT_NULL
        )
    }
}

val Context.database: CardDatabaseHandler
    get() = CardDatabaseHandler.getInstance(applicationContext)