package put.cardy.transaction

import android.content.Context
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.joda.time.DateTime
import put.cardy.database.DBContract
import put.cardy.database.DBContract.TransactionEntry.Companion.EXPENSE
import put.cardy.database.DBContract.TransactionEntry.Companion.TRANSACTION_TABLE_NAME
import put.cardy.database.database
import put.cardy.model.Transaction
import java.util.*

class TransactionRepository(val context: Context) {
    fun findByCardId(cardId: Long): ArrayList<Transaction> = context.database.use {
        val transactionList = ArrayList<Transaction>()

        select(
            TRANSACTION_TABLE_NAME,
            DBContract.DBEntry.ID,
            DBContract.DBEntry.CARD_ID,
            DBContract.DBEntry.DATE,
            EXPENSE
        ).whereArgs("cardId = {cardId}", "cardId" to cardId)
            .parseList(object : MapRowParser<List<Transaction>> {
                override fun parseRow(columns: Map<String, Any?>): List<Transaction> {
                    val id = columns.getValue(DBContract.DBEntry.ID)
                    val transactionCardId = columns.getValue(DBContract.DBEntry.CARD_ID)
                    val date = columns.getValue(DBContract.DBEntry.DATE)
                    val expense = columns.getValue(EXPENSE)

                    val transaction = Transaction(
                        id.toString().toLong(),
                        transactionCardId.toString().toLong(),
                        DateTime(date),
                        expense.toString().toDouble()
                    )

                    transactionList.add(transaction)

                    return transactionList
                }
            })

        transactionList
    }

    fun create(transaction: Transaction) = context.database.use {
        insert(
            DBContract.TransactionEntry.TRANSACTION_TABLE_NAME,
            DBContract.DBEntry.CARD_ID to transaction.cardId,
            DBContract.DBEntry.DATE to transaction.date.toString(),
            EXPENSE to transaction.expense
        )
    }

    fun delete(transaction: Transaction) = context.database.use {
        delete(
            DBContract.TransactionEntry.TRANSACTION_TABLE_NAME,
            whereClause = "id = {id}",
            args = *arrayOf("id" to transaction.id)
        )
    }
}