package put.cardy.model

data class Card(
    var id: Int = 0,
    var number: String = "",
    var bankName: String = "",
    var type: String = "",
    var time: String = "",
    var goal: Double = 0.0,
    var actualGoal: Double = 0.0
)