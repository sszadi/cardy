package put.cardy.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import put.cardy.model.Card

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DatabaseHandler.DATABASE_NAME, null, DatabaseHandler.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertCard(card: Card): Boolean {
        val db = writableDatabase

        val values = ContentValues()
        values.put(DBContract.CardEntry.CARD_ID, card.id)
        values.put(DBContract.CardEntry.NUMBER, card.number)
        values.put(DBContract.CardEntry.BANKNAME, card.bankName)

        db.insert(DBContract.CardEntry.TABLE_NAME, null, values)

        return true
    }


    @Throws(SQLiteConstraintException::class)
    fun deleteUser(userid: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.UserEntry.COLUMN_USER_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(userid)
        // Issue SQL statement.
        db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }


    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "FeedReader.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.CardEntry.TABLE_NAME + " (" +
                    DBContract.CardEntry.CARD_ID + " INTEGER PRIMARY KEY," +
                    DBContract.CardEntry.NUMBER + " REAL," +
                    DBContract.CardEntry.BANKNAME + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.CardEntry.TABLE_NAME
    }
}