package put.cardy.database

import android.content.Context
import org.jetbrains.anko.db.*
import put.cardy.database.DBContract.CardEntry.Companion.BANK_NAME
import put.cardy.database.DBContract.CardEntry.Companion.CARD_TABLE_NAME
import put.cardy.database.DBContract.CardEntry.Companion.NUMBER
import put.cardy.database.DBContract.DBEntry.Companion.ID
import put.cardy.database.DBContract.TransactionEntry.Companion.TRANSACTION_TABLE_NAME
import put.cardy.model.Card


class CardRepository(val context: Context) {

    fun findAll(): ArrayList<Card> = context.database.use {
        val cardList = ArrayList<Card>()

        select(
            CARD_TABLE_NAME,
            ID,
            NUMBER,
            BANK_NAME
        )
            .parseList(object : MapRowParser<List<Card>> {
                override fun parseRow(columns: Map<String, Any?>): List<Card> {
                    val id = columns.getValue(ID)
                    val number = columns.getValue(NUMBER)
                    val bankName = columns.getValue(BANK_NAME)

                    val card = Card(
                        id.toString().toLong(),
                        number.toString(),
                        bankName.toString()
                    )

                    cardList.add(card)

                    return cardList
                }
            })

        cardList
    }


    fun findById(id: Long): Card = context.database.use {
        var card: Card = Card()
        select(
            CARD_TABLE_NAME,
            ID,
            NUMBER,
            BANK_NAME
        ).whereArgs("id = {id}", "id" to id)
            .parseSingle(object : MapRowParser<Card> {
                override fun parseRow(columns: Map<String, Any?>): Card {
                    val cardId = columns.getValue(ID)
                    val number = columns.getValue(NUMBER)
                    val bankName = columns.getValue(BANK_NAME)

                    card = Card(
                        cardId.toString().toLong(),
                        number.toString(),
                        bankName.toString()
                    )

                    return card
                }
            })

        card
    }

    fun create(card: Card): Long = context.database.use {
        val id = insert(
            CARD_TABLE_NAME,
            NUMBER to card.number,
            BANK_NAME to card.bankName
        )
        id
    }

    fun update(card: Card) = context.database.use {
        update(
            CARD_TABLE_NAME,
            NUMBER to card.number,
            BANK_NAME to card.bankName
        )
            .whereArgs("id = {cardId}", "cardId" to card.id)
            .exec()
    }

    fun delete(card: Card) = context.database.use {
        delete(CARD_TABLE_NAME, whereClause = "id = {cardId}", args = *arrayOf("cardId" to card.id))
        delete(TRANSACTION_TABLE_NAME, whereClause = "cardId = {cardId}", args = *arrayOf("cardId" to card.id))
        delete(
            DBContract.GoalEntry.GOAL_TABLE_NAME,
            whereClause = "cardId = {cardId}",
            args = *arrayOf("cardId" to card.id)
        )
    }
}