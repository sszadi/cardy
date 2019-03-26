package put.cardy.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import put.cardy.database.DBContract.CardEntry.Companion.ACTUAL_GOAL
import put.cardy.database.DBContract.CardEntry.Companion.BANK_NAME
import put.cardy.database.DBContract.CardEntry.Companion.CARD_ID
import put.cardy.database.DBContract.CardEntry.Companion.GOAL
import put.cardy.database.DBContract.CardEntry.Companion.NUMBER
import put.cardy.database.DBContract.CardEntry.Companion.TABLE_NAME
import put.cardy.database.DBContract.CardEntry.Companion.TIME
import put.cardy.database.DBContract.CardEntry.Companion.TYPE


class CardDatabaseHandler(ctx: Context) : ManagedSQLiteOpenHelper(ctx, DBContract.CardEntry.TABLE_NAME, null, 1) {
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(DBContract.CardEntry.TABLE_NAME, true)
    }

    companion object {
        private var instance: CardDatabaseHandler? = null
        @Synchronized
        fun getInstance(ctx: Context): CardDatabaseHandler {
            if (instance == null) {
                instance = CardDatabaseHandler(ctx.getApplicationContext())
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            TABLE_NAME, true,
            CARD_ID to INTEGER + PRIMARY_KEY + UNIQUE,
            NUMBER to TEXT + NOT_NULL,
            BANK_NAME to TEXT + NOT_NULL,
            TYPE to TEXT + NOT_NULL,
            TIME to TEXT + NOT_NULL,
            GOAL to REAL + NOT_NULL,
            ACTUAL_GOAL to REAL + NOT_NULL
        )
    }
}

val Context.database: CardDatabaseHandler
    get() = CardDatabaseHandler.getInstance(applicationContext)