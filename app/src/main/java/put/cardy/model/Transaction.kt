package put.cardy.model

import org.joda.time.DateTime

class Transaction (
    var id: Long = 0,
    var cardId: Long = 0,
    var date: DateTime? = null,
    var expense: Double? = 0.0
)