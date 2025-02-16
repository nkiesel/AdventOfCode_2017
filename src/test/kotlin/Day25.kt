import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class Day25 {
    private val sample = """
        Begin in state A.
        Perform a diagnostic checksum after 6 steps.

        In state A:
          If the current value is 0:
            - Write the value 1.
            - Move one slot to the right.
            - Continue with state B.
          If the current value is 1:
            - Write the value 0.
            - Move one slot to the left.
            - Continue with state B.

        In state B:
          If the current value is 0:
            - Write the value 1.
            - Move one slot to the left.
            - Continue with state A.
          If the current value is 1:
            - Write the value 1.
            - Move one slot to the right.
            - Continue with state A.
    """.trimIndent().lines()

    data class State(val name: Char, val rules: List<Rule>) {
        companion object {
            fun from(s: List<String>): State {
                val name = Regex("""In state (.):""").find(s[0])!!.groupValues[1][0]
                return State(name, listOf(Rule.from(s[2] + s[3] + s[4]), Rule.from(s[6] + s[7] + s[8])))
            }
        }
    }

    data class Rule(val write: Int, val move: Int, val cont: Char) {
        companion object {
            fun from(s: String): Rule {
                val g =
                    Regex("""- Write the value (.).+- Move one slot to the (\w+).+- Continue with state (.)""").find(s)!!.groupValues
                return Rule(g[1].toInt(), if (g[2] == "left") -1 else 1, g[3][0])
            }
        }
    }

    private fun one(input: List<String>): Int {
        val c = input.chunkedBy(String::isEmpty)
        var state = Regex("""Begin in state (.)""").find(c[0][0])!!.groupValues[1][0]
        val steps = c[0][1].ints().first()
        val states = c.drop(1).map { State.from(it) }.associateBy { it.name }
        val ones = mutableSetOf<Int>()
        var cursor = 0
        repeat(steps) {
            with (states[state]!!.rules[if (cursor in ones) 1 else 0]) {
                if (write == 0) ones.remove(cursor) else ones.add(cursor)
                cursor += move
                state = cont
            }
        }
        return ones.size
    }

    @Test
    fun testOne(input: List<String>) {
        one(sample) shouldBe 3
        one(input) shouldBe 2832
    }
}
