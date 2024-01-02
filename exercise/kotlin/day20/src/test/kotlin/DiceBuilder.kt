class DiceBuilder private constructor(private val dice: IntArray) {
    fun build(): IntArray {
        return dice
    }

    override fun toString(): String = dice.contentToString()

    companion object {
        fun `🎲🎲🎲🎲🎲`(dice1: Int, dice2: Int, dice3: Int, dice4: Int, dice5: Int): DiceBuilder =
            DiceBuilder(intArrayOf(dice1, dice2, dice3, dice4, dice5))
    }
}