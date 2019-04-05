package put.cardy.database

import android.content.Context
import org.jetbrains.anko.db.*
import put.cardy.database.DBContract.CardEntry.Companion.ACTUAL_GOAL
import put.cardy.database.DBContract.CardEntry.Companion.BANK_NAME
import put.cardy.database.DBContract.CardEntry.Companion.CARD_ID
import put.cardy.database.DBContract.CardEntry.Companion.GOAL
import put.cardy.database.DBContract.CardEntry.Companion.NUMBER
import put.cardy.database.DBContract.CardEntry.Companion.TABLE_NAME
import put.cardy.database.DBContract.CardEntry.Companion.TIME
import put.cardy.database.DBContract.CardEntry.Companion.TYPE
import put.cardy.model.Card


class CardRepository(val context: Context) {

    fun findAll(): ArrayList<Card> = context.database.use {
        val cardList = ArrayList<Card>()

        select(
            TABLE_NAME,
            CARD_ID,
            NUMBER,
            BANK_NAME,
            TYPE,
            TIME,
            GOAL,
            ACTUAL_GOAL
        )
            .parseList(object : MapRowParser<List<Card>> {
                override fun parseRow(columns: Map<String, Any?>): List<Card> {
                    val id = columns.getValue(CARD_ID)
                    val number = columns.getValue(NUMBER)
                    val bankName = columns.getValue(BANK_NAME)
                    val type = columns.getValue(TYPE)
                    val time = columns.getValue(TIME)
                    val goal = columns.getValue(GOAL)
                    val actualGoal = columns.getValue(ACTUAL_GOAL)

                    val card = Card(
                        id.toString().toInt(),
                        number.toString(),
                        bankName.toString(),
                        type.toString(),
                        time.toString(),
                        goal.toString().toDouble(),
                        actualGoal.toString().toDouble()
                    )

                    cardList.add(card)

                    return cardList
                }
            })

        cardList
    }


    fun findById(id: Int): Card = context.database.use {
        var card: Card = Card()
        select(
            TABLE_NAME,
            CARD_ID,
            NUMBER,
            BANK_NAME,
            TYPE,
            TIME,
            GOAL,
            ACTUAL_GOAL
        ).whereArgs("id = {cardId}", "cardId" to id)
            .parseSingle(object : MapRowParser<Card> {
                override fun parseRow(columns: Map<String, Any?>): Card {
                    val id = columns.getValue(CARD_ID)
                    val number = columns.getValue(NUMBER)
                    val bankName = columns.getValue(BANK_NAME)
                    val type = columns.getValue(TYPE)
                    val time = columns.getValue(TIME)
                    val goal = columns.getValue(GOAL)
                    val actualGoal = columns.getValue(ACTUAL_GOAL)

                    card = Card(
                        id.toString().toInt(),
                        number.toString(),
                        bankName.toString(),
                        type.toString(),
                        time.toString(),
                        goal.toString().toDouble(),
                        actualGoal.toString().toDouble()
                    )

                    return card
                }
            })

        card
    }

    fun create(card: Card) = context.database.use {
        insert(
            TABLE_NAME,
            NUMBER to card.number,
            BANK_NAME to card.bankName,
            TYPE to card.type,
            TIME to card.time,
            GOAL to card.goal,
            ACTUAL_GOAL to card.actualGoal
        )
    }

    fun update(card: Card) = context.database.use {
        update(
            TABLE_NAME,
            NUMBER to card.number,
            BANK_NAME to card.bankName,
            TYPE to card.type,
            TIME to card.time,
            GOAL to card.goal,
            ACTUAL_GOAL to card.actualGoal
        )
            .whereArgs("id = {cardId}", "cardId" to card.id)
            .exec()
    }

    fun delete(card: Card) = context.database.use {
        delete(TABLE_NAME, whereClause = "id = {cardId}", args = *arrayOf("cardId" to card.id))
    }
}