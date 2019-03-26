package put.cardy.model

data class Card(
    var id: Int,
    var number: String,
    var bankName: String,
    var type: String,
    var time: String,
    var goal: Double,
    var actualGoal: Double
)