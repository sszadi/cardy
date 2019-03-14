package put.cardy.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
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
    fun deleteCard(cardId: Int): Boolean {
        val db = writableDatabase
        val selection = DBContract.CardEntry.CARD_ID + " LIKE ?"
        val selectionArgs = arrayOf(cardId.toString())
        db.delete(DBContract.CardEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun updateCard(card: Card): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DBContract.CardEntry.NUMBER, card.number)
        values.put(DBContract.CardEntry.BANKNAME, card.bankName)
        val _success = db.update(
            DBContract.CardEntry.TABLE_NAME,
            values,
            DBContract.CardEntry.CARD_ID + "=?",
            arrayOf(card.id.toString())
        ).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }


    fun readCard(cardId: Int): Card? {
        var card: Card? = null
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(
                "select * from " + DBContract.CardEntry.TABLE_NAME + " WHERE " + DBContract.CardEntry.CARD_ID + "='" + cardId + "'",
                null
            )
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return null
        }

        val number: String
        val bankName: String
        if (cursor!!.moveToFirst()) {
            number = cursor.getString(cursor.getColumnIndex(DBContract.CardEntry.NUMBER))
            bankName = cursor.getString(cursor.getColumnIndex(DBContract.CardEntry.BANKNAME))

            card = Card(cardId, number, bankName)
            cursor.close()

        }
        return card
    }

    fun readAllCards(): ArrayList<Card> {
        val users = ArrayList<Card>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.CardEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var cardId: Int
        var number: String
        var bankName: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                cardId = cursor.getInt(cursor.getColumnIndex(DBContract.CardEntry.CARD_ID))
                number = cursor.getString(cursor.getColumnIndex(DBContract.CardEntry.NUMBER))
                bankName = cursor.getString(cursor.getColumnIndex(DBContract.CardEntry.BANKNAME))

                users.add(Card(cardId, number, bankName))
                cursor.moveToNext()
            }
        }
        return users
    }


    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Card.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.CardEntry.TABLE_NAME + " (" +
                    DBContract.CardEntry.CARD_ID + " INTEGER PRIMARY KEY," +
                    DBContract.CardEntry.NUMBER + " REAL," +
                    DBContract.CardEntry.BANKNAME + " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.CardEntry.TABLE_NAME
    }
}