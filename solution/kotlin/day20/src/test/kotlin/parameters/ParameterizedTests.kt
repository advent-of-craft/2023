package parameters

import builders.DiceBuilder

data class NumberRollCase(val dice: DiceBuilder, val number: Int, val expectedResult: Int)
data class RollCase(val dice: DiceBuilder, val expectedResult: Int)
class InvalidRoll(vararg val dice: Int)