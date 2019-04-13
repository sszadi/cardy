package put.cardy.model


enum class GoalType(val text: String) {
    AMOUNT("Amount of transactions"),
    EXPENSE("Transactions expense");

    companion object {
        fun getTypeByText(text: String): GoalType? {
            for (goalType in GoalType.values()) {
                if (goalType.text == text) {
                    return goalType
                }
            }
            return null
        }
    }
}